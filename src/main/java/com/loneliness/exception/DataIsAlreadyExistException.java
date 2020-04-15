package com.loneliness.exception;

public class DataIsAlreadyExistException extends RuntimeException {
    public DataIsAlreadyExistException() {
    }

    public DataIsAlreadyExistException(String message) {
        super(message);
    }

    public DataIsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataIsAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public DataIsAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
