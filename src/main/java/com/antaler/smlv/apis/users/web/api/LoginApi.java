package com.antaler.smlv.apis.users.web.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.antaler.smlv.apis.users.model.api.login.LoginDTO;

import reactor.core.publisher.Mono;


@RequestMapping("v1/login")
public interface LoginApi {


    @PostMapping
    Mono<ResponseEntity<Object>> loginUserAndPassword(@RequestBody LoginDTO loginDto);


    @PostMapping("twofa")
    Mono<ResponseEntity<Object>> loginUserAndTwoFa(@RequestBody LoginDTO loginDto);


    @GetMapping("refresh")
    Mono<ResponseEntity<Object>> refresh(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String token);
    


}
