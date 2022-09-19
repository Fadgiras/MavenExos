package com.fadgiras.exos.exception;

public class StorageException extends Exception {

    private String errorCode;

    public StorageException(String message,  String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public StorageException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
