package com.ssafy.apm.common.exceptions;

public class CustomValidationException extends RuntimeException {

    public CustomValidationException(Long id) {
        super("Invalidate Entity with id: " + id);
    }

    public CustomValidationException(String message) {
        super(message);
    }

    public CustomValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
