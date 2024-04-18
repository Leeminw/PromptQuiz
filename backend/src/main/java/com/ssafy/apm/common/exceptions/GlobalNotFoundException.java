package com.ssafy.apm.common.exceptions;

public class GlobalNotFoundException extends RuntimeException {

    public GlobalNotFoundException(Long id) {
        super("Entity Not Found with id: " + id);
    }

    public GlobalNotFoundException(String message) {
        super(message);
    }

    public GlobalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
