package com.smartcontactmanager.scm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private int httpStatus;
    private int apiErrorCode;
    private String errorMessage;
}
