package com.ssafy.apm.channel.exception;

public class ChannelValidationException extends RuntimeException {

    public ChannelValidationException(Long id) {
        super("Invalidate Channel Entity with id: " + id);
    }

    public ChannelValidationException(String message) {
        super(message);
    }

    public ChannelValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
