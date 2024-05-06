package com.ssafy.apm.multiplechoice.exception;

public class MultipleChoiceNotFoundException extends RuntimeException {

    public MultipleChoiceNotFoundException(Long id) {
        super("Entity Not Found with id: " + id);
    }

    public MultipleChoiceNotFoundException(String message) {
        super(message);
    }

    public MultipleChoiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
