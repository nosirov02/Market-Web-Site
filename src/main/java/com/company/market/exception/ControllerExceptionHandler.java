package com.company.market.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({ServerBadRequestException.class})
    public ResponseEntity<?> handlerException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({ProfileNotFoundException.class})
    public ResponseEntity<?> handlerException(ProfileNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
