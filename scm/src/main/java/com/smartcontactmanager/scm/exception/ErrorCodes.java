package com.smartcontactmanager.scm.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorCodes {
    public static ErrorCode INVALID_IMAGE_CONTENT_TYPE = new ErrorCode(1, "invalid.image.content.type");
    public static ErrorCode USER_NOT_FOUND = new ErrorCode(2, "user.not.found");
}
