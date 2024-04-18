package com.ssafy.apm.common.exceptions;

public class GlobalValidationException  extends RuntimeException {

    public GlobalValidationException(Long id) {
        super("Invalidate Entity with id: " + id);
    }

    public GlobalValidationException(String message) {
        super(message);
    }

    public GlobalValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
