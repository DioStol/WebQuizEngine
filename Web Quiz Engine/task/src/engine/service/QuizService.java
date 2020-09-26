package engine.service;

import engine.exception.QuizNotFoundException;
import engine.model.Answer;
import engine.model.Completion;
import engine.model.Question;
import engine.model.User;
import engine.repository.QuizRepository;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Dionysios Stolis <dionstol@gmail.com>
 */
@Service
public class QuizService {

    private QuizRepository quizRepository;

    private UserRepository userRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
    }

    public Question add(Question question) {
        return quizRepository.save(question);
    }

    public Question get(long id) {
        return quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
    }

    public Page<Question> getAll(Pageable pageable) {
        return quizRepository.findAll(pageable);
    }

    public Answer solve(long id, List<Integer> guesses, User user) {
        List<Integer> answers = get(id).getAnswer();
        boolean isSolved = guesses.containsAll(answers) && answers.containsAll(guesses);
        if (isSolved){
            user.setSolvedQuizzes(Stream.concat(List.of(new Completion(id)).stream(), user.getSolvedQuizzes().stream()).collect(Collectors.toList()));
            userRepository.save(user);
            return Answer.CORRECT;
        }else {
            return Answer.INCORRECT;
        }
    }

    public void deleteQuizById(long id) {
        quizRepository.deleteById(id);
    }
}
