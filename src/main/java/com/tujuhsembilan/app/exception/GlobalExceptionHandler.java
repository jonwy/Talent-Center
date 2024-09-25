package com.tujuhsembilan.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tujuhsembilan.app.dto.response.MessageResponse;
import com.tujuhsembilan.app.util.ResponseUtil;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<MessageResponse> dataNotFoundException(DataNotFoundException ex) {
        return ResponseUtil.createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    
}
