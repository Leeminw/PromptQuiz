package com.ssafy.apm.channel.exception;

public class ChannelNotFoundException extends RuntimeException {

    public ChannelNotFoundException(Long id) {
        super("Entity Not Found with id: " + id);
    }

    public ChannelNotFoundException(String message) {
        super(message);
    }

    public ChannelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
