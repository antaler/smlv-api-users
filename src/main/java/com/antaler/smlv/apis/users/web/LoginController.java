package com.antaler.smlv.apis.users.web;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.antaler.smlv.apis.users.model.TOTPQrKey;
import com.antaler.smlv.apis.users.model.endpoints.SignInRequest;
import com.antaler.smlv.apis.users.model.endpoints.SignInResponse;
import com.antaler.smlv.apis.users.services.login.LoginService;
import com.antaler.smlv.apis.users.services.totp.TOTPService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v1/login")
public class LoginController {

    @Autowired
    private TOTPService totp;

    @Autowired
    private LoginService login;

    @PostMapping("sign-in")
    public Mono<ResponseEntity<SignInResponse>> login(@RequestBody SignInRequest request) {
       return Mono.just(request).flatMap(req -> {
            return (Objects.isNull(req.email()) || req.email().isBlank()) || (Objects.isNull(req.password()) || req.password().isBlank()) ?  Mono.error(new RuntimeException("Validations fails")): Mono.just(req) ;
        }).flatMap(login::login)
        .map(ResponseEntity::ok)
        .switchIfEmpty(Mono.just(new ResponseEntity<SignInResponse>(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @PostMapping("sign-up")
    public String validateInvitationToken(@RequestBody String entity) {
        return entity;
    }

    @GetMapping("sign-up/validate-invitation")
    public String registerUser(@RequestParam(name = "invitationToken", required = true) String invitationToken) {
        return invitationToken;
    }

    @PostMapping("refresh")
    public String refreshToken(@RequestBody String entity) {
        return entity;
    }

    @GetMapping("2fa")
    public Mono<TOTPQrKey> create2fa(@RequestParam(name = "user") String user) {
        return totp.generateKey(user);
    }
    
}
