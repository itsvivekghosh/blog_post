package com.vivekghosh.springboottutorials.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.vivekghosh.springboottutorials.Payloads.GenericPayload;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    // handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericPayload> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
    	GenericPayload errorDetails = new GenericPayload(new Date(), exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    
    // global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericPayload> handleGlobalException(Exception exception, WebRequest webRequest){
    	GenericPayload errorDetails = new GenericPayload(new Date(), exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

