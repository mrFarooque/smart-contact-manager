package com.smartcontactmanager.scm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;
    private Object[] arguments;
}
