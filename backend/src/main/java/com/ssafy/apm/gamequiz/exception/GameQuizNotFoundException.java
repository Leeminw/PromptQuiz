package com.ssafy.apm.gamequiz.exception;

public class GameQuizNotFoundException extends RuntimeException {

    public GameQuizNotFoundException(Long id) {
        super("Entity Not Found with id: " + id);
    }

    public GameQuizNotFoundException(String message) {
        super(message);
    }

    public GameQuizNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
