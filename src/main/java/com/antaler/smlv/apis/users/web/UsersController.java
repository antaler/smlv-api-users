package com.antaler.smlv.apis.users.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antaler.smlv.apis.users.model.db.UserEntity;
import com.antaler.smlv.apis.users.services.users.UserService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v1/users")
@Slf4j
public class UsersController {

    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("invite")
    public String inviteUser(@RequestBody String entity) {
        return entity;
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> create(@RequestBody UserEntity entity) {
        return userService.create(entity)
                .map(v -> new ResponseEntity<Object>(HttpStatus.BAD_REQUEST))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CREATED))
                .onErrorResume(err -> {
                    log.error(err.getMessage());
                    var response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body((Object) Map.of("error", err.getMessage()));
                    return Mono.just(response);
                });
    }

}
