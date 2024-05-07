package com.ssafy.apm.gameuser.exception;

public class GameUserNotFoundException extends RuntimeException {

    public GameUserNotFoundException(Long id) {
        super("Entity Not Found with id: " + id);
    }

    public GameUserNotFoundException(String message) {
        super(message);
    }

    public GameUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
