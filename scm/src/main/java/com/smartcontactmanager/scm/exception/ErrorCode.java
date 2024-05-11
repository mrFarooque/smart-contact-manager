package com.smartcontactmanager.scm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ErrorCode {
   private int apiErrorCode;
   private String errorCode;
}
