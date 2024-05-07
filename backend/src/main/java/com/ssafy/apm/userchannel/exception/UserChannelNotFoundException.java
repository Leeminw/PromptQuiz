package com.ssafy.apm.userchannel.exception;

public class UserChannelNotFoundException extends RuntimeException {

    public UserChannelNotFoundException(Long id) {
        super("Entity Not Found with id: " + id);
    }

    public UserChannelNotFoundException(String message) {
        super(message);
    }

    public UserChannelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
