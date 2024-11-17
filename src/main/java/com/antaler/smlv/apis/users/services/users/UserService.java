package com.antaler.smlv.apis.users.services.users;

import com.antaler.smlv.apis.users.model.db.UserEntity;

import reactor.core.publisher.Mono;

public sealed interface UserService permits UserServiceImpl {

    Mono<Void> create(UserEntity user);

}
