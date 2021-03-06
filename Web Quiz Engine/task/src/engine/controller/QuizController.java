package engine.controller;

import engine.model.Answer;
import engine.model.Completion;
import engine.model.Question;
import engine.model.User;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Dionysios Stolis <dionstol@gmail.com>
 */
@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private QuizService service;
    
    @Autowired
    public QuizController(QuizService service) {
        this.service = service;
    }

    @PostMapping
    public Question addQuestion(@Valid @RequestBody Question question) {
        question.setAuthor((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return service.add(question);
    }

    @GetMapping
    public Page<Question> getAll(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return service.getAll(pageable);
    }

    @GetMapping(path = "/{id}")
    public Question getQuestion(@PathVariable int id) {
        return service.get(id);
    }

    @PostMapping(path = "/{id}/solve")
    public Answer checkAnswer(@PathVariable int id, @RequestBody Map<String, List<Integer>> body, @AuthenticationPrincipal User user) {
        return service.solve(id, body.get("answer"), user);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable long id) {
        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (! loggedIn.getUsername().equals(service.get(id).getAuthor().getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author of this quiz.");
        service.deleteQuizById(id);
    }

    @GetMapping("/completed")
    public Page<Completion> getSolvedQuizzes(@RequestParam(required = false) Integer page, @AuthenticationPrincipal User user) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, 10);
        List<Completion> solvedQuizzes = user.getSolvedQuizzes();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), solvedQuizzes.size());
        return new PageImpl<>(user.getSolvedQuizzes().subList(start, end), pageable, solvedQuizzes.size());
    }
}
