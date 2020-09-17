package engine.service;

import engine.exception.QuizNotFoundException;
import engine.model.Answer;
import engine.model.Question;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dionysios Stolis <dionstol@gmail.com>
 */
@Service
public class QuizService {

    private QuizRepository repository;

    @Autowired
    public QuizService(QuizRepository repository) {
        this.repository = repository;
    }

    public Question add(Question question) {
        return repository.save(question);
    }

    public Question get(long id) {
        return repository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
    }

    public Page<Question> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Answer solve(long id, List<Integer> guesses) {
        List<Integer> answers = get(id).getAnswer();
        return guesses.containsAll(answers) && answers.containsAll(guesses) ? Answer.CORRECT : Answer.INCORRECT;
    }

    public void deleteQuizById(long id) {
        repository.deleteById(id);
    }
}
