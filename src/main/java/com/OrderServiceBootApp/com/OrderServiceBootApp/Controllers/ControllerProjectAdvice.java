package com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers;

import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerProjectAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex){
return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);


    }

}