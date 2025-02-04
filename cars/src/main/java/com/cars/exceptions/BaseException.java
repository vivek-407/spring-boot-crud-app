package com.cars.exceptions;

public class BaseException extends RuntimeException {

    private final int status;
    private final String errorCode;
    private final String errorMessage;
    private final String developerMessage;

    public BaseException(int httpStatus, String errorCode,
                         String errorMessage, String developerMessage) {
        super(developerMessage);
        this.status = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.developerMessage = developerMessage;

    }

    public int getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDeveloperMessage() {
        return developerMessage;

    }
}

