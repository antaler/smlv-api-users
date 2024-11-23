package com.antaler.smlv.apis.users.services.login;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.antaler.smlv.apis.users.model.api.exceptions.ApiException;
import com.antaler.smlv.apis.users.model.api.login.LoginDTO;
import com.antaler.smlv.apis.users.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public final class LoginServiceImpl implements LoginService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public LoginServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<Object> loginUserAndPassword(LoginDTO login) {
        return Mono.just(login).flatMap((LoginDTO lg) -> {
            var user = userRepository.findFirstByEmail(lg.user());
            if (user.isEmpty()) {
                return Mono.error(ApiException.builder().code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).status(HttpStatus.UNAUTHORIZED).build());
            }

            if (!passwordEncoder.matches(lg.password(), user.get().getPassword())) {
                return Mono.error(ApiException.builder().code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).status(HttpStatus.UNAUTHORIZED).build());
            }

            return Mono.just(new Object());

        });
    }

}
