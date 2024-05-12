package com.smartcontactmanager.scm.exception;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(ErrorCode errorCode, String errorMessage, String... arguments) {
        super(errorCode, errorMessage, arguments);
    }
}
