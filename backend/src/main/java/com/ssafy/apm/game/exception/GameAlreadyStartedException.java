package com.ssafy.apm.game.exception;

public class GameAlreadyStartedException extends RuntimeException {

    public GameAlreadyStartedException() {
        super("Game already started");
    }

    public GameAlreadyStartedException(String code) {
        super("Game already started with code: " + code);
    }


    public GameAlreadyStartedException(String message, Throwable cause) {
        super(message, cause);
    }

}
