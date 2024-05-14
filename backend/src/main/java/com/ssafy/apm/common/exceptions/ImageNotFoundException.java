package com.ssafy.apm.common.exceptions;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(Long id) {
        super("Entity Not Found with id: " + id);
    }

    public ImageNotFoundException(String message) {
        super(message);
    }

    public ImageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
