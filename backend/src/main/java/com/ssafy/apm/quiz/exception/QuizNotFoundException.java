package com.ssafy.apm.quiz.exception;

public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException(Long id) {
        super("Entity Not Found with id: " + id);
    }

    public QuizNotFoundException(String message) {
        super(message);
    }

    public QuizNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
