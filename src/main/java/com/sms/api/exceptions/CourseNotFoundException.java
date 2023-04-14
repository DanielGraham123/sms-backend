package com.sms.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends Exception{
    public CourseNotFoundException(String message) {
        super(message);
    }
}
