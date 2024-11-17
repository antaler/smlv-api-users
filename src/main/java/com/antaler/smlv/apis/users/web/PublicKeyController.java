package com.antaler.smlv.apis.users.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.antaler.smlv.apis.users.services.key.PublicKeyService;

import reactor.core.publisher.Mono;


@RequestMapping("v1/public-key")
@RestController
public class PublicKeyController {

    private final PublicKeyService keyService;


    public PublicKeyController(PublicKeyService keyService){
        this.keyService = keyService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<String> getPublicKey() {
        return keyService.getPublicKey();
    }
    

    

}
