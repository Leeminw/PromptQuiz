package com.ssafy.apm.quiz.exception;

public class QuizValidationException extends RuntimeException {

    public QuizValidationException(Long id) {
        super("Invalidate Entity with id: " + id);
    }

    public QuizValidationException(String message) {
        super(message);
    }

    public QuizValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
