package com.antaler.smlv.apis.users.model.api.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApiException extends RuntimeException {


    private String code;

    private String message;

    private HttpStatus status;

}
