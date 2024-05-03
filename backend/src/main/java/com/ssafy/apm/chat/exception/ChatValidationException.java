package com.ssafy.apm.chat.exception;

public class ChatValidationException extends RuntimeException {

    public ChatValidationException(String message) {
        super(message);
    }

    public ChatValidationException(Object chat) {
        super("Chat Validation Exception : " + chat.toString());
    }

    public ChatValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
