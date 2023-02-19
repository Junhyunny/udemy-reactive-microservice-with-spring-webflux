package com.example.handler;

import com.example.dto.InputFailedValidationResponse;
import com.example.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException exception) {
        InputFailedValidationResponse response = new InputFailedValidationResponse();
        response.setErrorCode(InputValidationException.getErrorCode());
        response.setInput(exception.getInput());
        response.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
