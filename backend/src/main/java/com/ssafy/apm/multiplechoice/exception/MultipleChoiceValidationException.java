package com.ssafy.apm.multiplechoice.exception;

public class MultipleChoiceValidationException extends RuntimeException {

    public MultipleChoiceValidationException(Long id) {
        super("Invalidate Entity with id: " + id);
    }

    public MultipleChoiceValidationException(String message) {
        super(message);
    }

    public MultipleChoiceValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
