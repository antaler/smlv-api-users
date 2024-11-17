package com.antaler.smlv.apis.users.web;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.antaler.smlv.apis.users.model.api.exceptions.ApiException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ApiException.class)
    ResponseEntity<Map<String,String>> apiException(ApiException ex){
        var body = Map.of("code", ex.getCode(), "message", ex.getMessage());
        var response = ResponseEntity.status(ex.getStatus()).body(body);  
        return response;
    }
}
