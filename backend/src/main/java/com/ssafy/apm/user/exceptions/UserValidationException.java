package com.ssafy.apm.user.exceptions;

public class UserValidationException extends RuntimeException {

    public UserValidationException(Long id) {
        super("Invalidate Entity with id: " + id);
    }

    public UserValidationException(String message) {
        super(message);
    }

    public UserValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
