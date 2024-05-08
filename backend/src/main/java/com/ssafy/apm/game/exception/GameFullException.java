package com.ssafy.apm.game.exception;

public class GameFullException extends RuntimeException {

    public GameFullException() {
        super("Game already full");
    }

    public GameFullException(String code) {
        super("Game already full with code: " + code);
    }


    public GameFullException(String message, Throwable cause) {
        super(message, cause);
    }

}
