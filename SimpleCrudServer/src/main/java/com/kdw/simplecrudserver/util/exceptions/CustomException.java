package com.kdw.simplecrudserver.util.exceptions;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private int status;
    public CustomException(int status, String message) {
        super(message);
        this.status = status;
    }
}
