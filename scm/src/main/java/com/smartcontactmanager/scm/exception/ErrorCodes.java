package com.smartcontactmanager.scm.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorCodes {
    public static ErrorCode INVALID_IMAGE_CONTENT_TYPE = new ErrorCode(1, "invalid.image.content.type");
    public static ErrorCode USER_NOT_FOUND = new ErrorCode(2, "user.not.found");
    public static ErrorCode INVALID_BASIC_AUTH = new ErrorCode(3, "invalid.basic.auth");
    public static ErrorCode AUTHORIZATION_HEADER_NOT_PRESENT = new ErrorCode(4, "authorization.header.not.present");
    public static ErrorCode AUTHORIZATION_TOKEN_EXPIRED = new ErrorCode(5, "authorization.token.expired");
    public static ErrorCode AUTHORIZATION_TOKEN_INVALID = new ErrorCode(5, "authorization.token.invalid");
}
