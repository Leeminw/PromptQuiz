package com.ssafy.apm.game.exception;

public class GameValidationException extends RuntimeException {

    public GameValidationException(Long id) {
        super("Invalidate Entity with id: " + id);
    }

    public GameValidationException(String message) {
        super(message);
    }

    public GameValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
