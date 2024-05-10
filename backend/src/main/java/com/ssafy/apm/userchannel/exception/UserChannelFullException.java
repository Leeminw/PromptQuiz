package com.ssafy.apm.userchannel.exception;

public class UserChannelFullException extends RuntimeException{

    public UserChannelFullException() {
        super("UserChannel already full");
    }

    public UserChannelFullException(String code) {
        super("UserChannel already full with code: " + code);
    }


    public UserChannelFullException(String message, Throwable cause) {
        super(message, cause);
    }

}
