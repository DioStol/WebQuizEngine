package engine.model;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Dionysios Stolis <dionstol@gmail.com>
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  Answer {

    CORRECT( true, "Congratulations, you're right!"),
    INCORRECT(false, "Wrong answer! Please, try again.");

    private final boolean success;
    private final String feedback;

    Answer(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
