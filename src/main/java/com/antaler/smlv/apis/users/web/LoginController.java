package com.antaler.smlv.apis.users.web;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.antaler.smlv.apis.users.model.api.exceptions.ApiException;
import com.antaler.smlv.apis.users.model.api.login.LoginDTO;
import com.antaler.smlv.apis.users.services.login.LoginService;
import com.antaler.smlv.apis.users.web.api.LoginApi;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class LoginController implements LoginApi {

    private LoginService loginService;

    private LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public Mono<ResponseEntity<Object>> loginUserAndPassword(@RequestBody LoginDTO loginDto) {
        return Mono.just(loginDto).flatMap((LoginDTO lgDto) -> {

            if (Objects.isNull(loginDto.user()) || loginDto.user().isEmpty()) {
                return Mono.error(ApiException.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message("the field user is mandatory").status(HttpStatus.BAD_REQUEST).build());
            }
            if (Objects.isNull(loginDto.password()) || loginDto.password().isEmpty()) {
                return Mono.error(ApiException.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message("the field password is mandatory").status(HttpStatus.BAD_REQUEST).build());
            }
            return Mono.just(lgDto);
        }).flatMap(loginService::loginUserAndPassword).map(obj -> ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<Object>> loginUserAndTwoFa(@RequestBody LoginDTO loginDto) {
        return Mono.just(loginDto).flatMap((LoginDTO lgDto) -> {

            if (Objects.isNull(loginDto.user()) || loginDto.user().isEmpty()) {
                return Mono.error(ApiException.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message("the field user is mandatory").status(HttpStatus.BAD_REQUEST).build());
            }
            if (Objects.isNull(loginDto.code()) || loginDto.code().isEmpty()) {
                return Mono.error(ApiException.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message("the field code is mandatory").status(HttpStatus.BAD_REQUEST).build());
            }
            return Mono.just(lgDto);
        }).flatMap(loginService::loginUserAndTwoFa).map(tkn -> ResponseEntity.ok(Map.of("token", tkn)));
    }

    @Override
    public Mono<ResponseEntity<Object>> refresh(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String token) {
        return Mono.just(token).map(tkn -> tkn.startsWith("Bearer ") ? tkn.replace("Bearer ", "") : tkn)
                .flatMap(loginService::refresh).map(tkn -> ResponseEntity.ok(Map.of("token", tkn)));
    }

}
