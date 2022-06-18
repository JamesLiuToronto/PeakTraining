package com.peak.training.common.annotation.validation.exception;

public class TooManyCallsException extends RuntimeException {

    public TooManyCallsException(String errorCode) {
        super(errorCode);
    }
}
