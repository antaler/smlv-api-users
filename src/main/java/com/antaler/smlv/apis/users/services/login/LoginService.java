package com.antaler.smlv.apis.users.services.login;

import com.antaler.smlv.apis.users.model.api.login.LoginDTO;

import reactor.core.publisher.Mono;

public sealed interface LoginService permits LoginServiceImpl{


  
    Mono<Object> loginUserAndPassword(LoginDTO login);




}
