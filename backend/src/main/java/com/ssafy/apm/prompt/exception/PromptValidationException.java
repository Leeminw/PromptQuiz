package com.ssafy.apm.prompt.exception;

public class PromptValidationException extends RuntimeException {

    public PromptValidationException(Long id) {
        super("Invalidate Entity with id: " + id);
    }

    public PromptValidationException(String message) {
        super(message);
    }

    public PromptValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
