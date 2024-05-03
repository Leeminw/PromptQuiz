package com.ssafy.apm.prompt.exception;

public class PromptNotFoundException extends RuntimeException {

    public PromptNotFoundException(Long id) {
        super("Entity Not Found with id: " + id);
    }

    public PromptNotFoundException(String message) {
        super(message);
    }

    public PromptNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
