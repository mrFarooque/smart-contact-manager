package com.smartcontactmanager.scm.exception;

public class InvalidRequestException extends BaseException {

    public InvalidRequestException(ErrorCode errorCode, String errorMessage, String... arguments) {
        super(errorCode, errorMessage, arguments);
    }
}
