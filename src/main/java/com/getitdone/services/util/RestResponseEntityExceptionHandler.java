package com.getitdone.services.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public static String errorTemplate = "{\n" +
            "  \"errorCode\": \"$errorCode\"\n" +
            "  \"message\": \"Sorry, something went wrong\"\n" +
            "  \"details\": \"$message\"\n" +
            "}";

    @ExceptionHandler(value = { GetitDonAppException.class, Throwable.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "System Error";
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;

        if(ex instanceof GetitDonAppException ) {
            GetitDonAppException exp = (GetitDonAppException) ex;
            bodyOfResponse = errorTemplate.replaceAll("$errorCode", exp.getCode()).replaceAll("$message", ex.getMessage());
            if(exp.getHttpStatus() != null) {
                code = exp.getHttpStatus();
            }
            //TODO: do proper error message based up on exp
        }
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), code, request);
    }
}
