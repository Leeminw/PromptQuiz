package com.ssafy.apm.common.exceptions;

public class ImageValidationException extends RuntimeException {
    public ImageValidationException(Long id) {
        super("Invalidate Entity with id: " + id);
    }

    public ImageValidationException(String message) {
        super(message);
    }

    public ImageValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
