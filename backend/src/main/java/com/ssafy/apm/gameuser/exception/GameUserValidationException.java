package com.ssafy.apm.gameuser.exception;

public class GameUserValidationException extends RuntimeException {

    public GameUserValidationException(Long id) {
        super("Invalidate Entity with id: " + id);
    }

    public GameUserValidationException(String message) {
        super(message);
    }

    public GameUserValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
