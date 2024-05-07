package com.ssafy.apm.userchannel.exception;

public class UserChannelValidationException extends RuntimeException {

    public UserChannelValidationException(Long id) {
        super("Invalidate Entity with id: " + id);
    }

    public UserChannelValidationException(String message) {
        super(message);
    }

    public UserChannelValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
