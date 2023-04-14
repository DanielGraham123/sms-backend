package com.sms.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GradeNotFoundException extends Exception {
    public GradeNotFoundException(String message) {
        super(message);
    }
}
