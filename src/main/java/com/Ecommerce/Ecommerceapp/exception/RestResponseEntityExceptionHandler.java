package com.Ecommerce.Ecommerceapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorMessage> usernotfoundexception(
            GlobalException exception, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return ResponseEntity.ok(errorMessage);
    }

}
