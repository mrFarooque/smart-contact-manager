package com.smartcontactmanager.scm.controller;

import com.smartcontactmanager.scm.exception.ErrorMessage;
import com.smartcontactmanager.scm.exception.InvalidRequestException;
import com.smartcontactmanager.scm.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class ExceptionHandlerController {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = {InvalidRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage invalidRequestException(InvalidRequestException ex, Locale locale) {
        return new ErrorMessage(400, ex.getErrorCode().getApiErrorCode(),
                messageSource.getMessage(ex.getErrorCode().getErrorCode(), ex.getArguments(), LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, Locale locale) {
        return new ErrorMessage(400, ex.getErrorCode().getApiErrorCode(),
                messageSource.getMessage(ex.getErrorCode().getErrorCode(), ex.getArguments(), LocaleContextHolder.getLocale()));
    }

}
