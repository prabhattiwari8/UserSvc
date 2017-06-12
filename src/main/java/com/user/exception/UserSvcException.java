package com.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserSvcException extends Exception{

    public UserSvcException(String message) {
        super(message);
    }

    public UserSvcException(String message, Throwable cause) {
        super(message, cause);
    }
}
