package com.kdw.simplecrudserver.util.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> CustomException(final CustomException e){
        Map<String, String> body = new HashMap<>();
        body.put("message", e.getMessage());

        return ResponseEntity
            .status(e.getStatus())
            .body(body);
    }
}
