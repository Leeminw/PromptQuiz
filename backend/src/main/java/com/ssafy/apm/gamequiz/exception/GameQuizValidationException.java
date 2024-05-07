package com.ssafy.apm.gamequiz.exception;

public class GameQuizValidationException extends RuntimeException {

    public GameQuizValidationException(Long id) {
        super("Invalidate Entity with id: " + id);
    }

    public GameQuizValidationException(String message) {
        super(message);
    }

    public GameQuizValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
