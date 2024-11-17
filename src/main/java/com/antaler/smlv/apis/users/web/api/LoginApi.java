package com.antaler.smlv.apis.users.web.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antaler.smlv.apis.users.model.api.login.LoginDTO;

import reactor.core.publisher.Mono;


@RequestMapping("v1/login")
public interface LoginApi {


    @PostMapping
    public Mono<ResponseEntity<Object>> loginUserAndPassword(@RequestBody LoginDTO loginDto);

    


}
