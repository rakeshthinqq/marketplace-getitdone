package com.getitdone.services.util;

import org.springframework.http.HttpStatus;

public class GetitDonAppException extends  RuntimeException {

    private final String code;
    private HttpStatus httpStatus = null;

    public GetitDonAppException(HttpStatus status, String code, String message){
        super(message);
        this.code = code;
        this.httpStatus = status;
    }

    public GetitDonAppException(String code, String message){
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
