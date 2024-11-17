package com.antaler.smlv.apis.users.services.login;

import com.antaler.smlv.apis.users.model.endpoints.SignInRequest;
import com.antaler.smlv.apis.users.model.endpoints.SignInResponse;

import reactor.core.publisher.Mono;

public interface LoginService {


    Mono<SignInResponse> login(SignInRequest request);
    
    Mono<Boolean> validateInvitation(String invitationToken);


    Mono<SignInResponse> registerUser(String invitationToken, Object user)




}
