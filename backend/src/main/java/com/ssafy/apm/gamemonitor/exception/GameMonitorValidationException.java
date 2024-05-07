package com.ssafy.apm.gamemonitor.exception;

public class GameMonitorValidationException extends RuntimeException {

    public GameMonitorValidationException(String message) {
        super(message);
    }

    public GameMonitorValidationException(Object gameMonitor) {
        super("GameMonitor Validation Exception : " + gameMonitor.toString());
    }

    public GameMonitorValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
