package com.ssafy.apm.chat.exception;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException(String message) {
        super(message);
    }

    public ChatNotFoundException(Integer hour) {
        super("Empty duration : " + hour);
    }

    public ChatNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
