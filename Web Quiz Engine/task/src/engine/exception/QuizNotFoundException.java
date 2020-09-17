package engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dionysios Stolis <dionstol@gmail.com>
 */
@ResponseBody
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException(long id) {
        super("Question with id = " + id + " doesn't exist.");
    }
}
