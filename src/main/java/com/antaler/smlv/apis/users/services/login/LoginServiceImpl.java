package com.antaler.smlv.apis.users.services.login;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.antaler.smlv.apis.users.model.api.exceptions.ApiException;
import com.antaler.smlv.apis.users.model.api.login.LoginDTO;
import com.antaler.smlv.apis.users.model.db.UserLoginStatusEnum;
import com.antaler.smlv.apis.users.services.TokenService.TokenService;
import com.antaler.smlv.apis.users.repository.UserRepository;
import com.antaler.smlv.apis.users.services.totp.TOTPService;

import reactor.core.publisher.Mono;

@Service
public final class LoginServiceImpl implements LoginService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private TOTPService totpService;

    private TokenService tokenService;

    public LoginServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TOTPService totpService,
            TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.totpService = totpService;
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Object> loginUserAndPassword(LoginDTO login) {
        return Mono.just(login).map((LoginDTO lg) -> {
            var user = userRepository.findFirstByEmail(lg.user())
                    .orElseThrow(() -> ApiException.builder().code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                            .message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).status(HttpStatus.UNAUTHORIZED)
                            .build());

            if (!passwordEncoder.matches(lg.password(), user.getPassword())) {
                throw ApiException.builder().code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).status(HttpStatus.UNAUTHORIZED).build();
            }

            user.setStatusLogin(UserLoginStatusEnum.TWO_FA.name());
            userRepository.save(user);

            return Mono.just(new Object());

        });
    }

    @Override
    public Mono<String> loginUserAndTwoFa(final LoginDTO login) {
        return Mono.justOrEmpty(userRepository.findFirstByEmail(login.user()))
                .switchIfEmpty(Mono.error(ApiException.builder().code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).status(HttpStatus.UNAUTHORIZED).build()))
                .flatMap(user -> UserLoginStatusEnum.valueOf(user.getStatusLogin()) == UserLoginStatusEnum.TWO_FA
                        ? Mono.just(user)
                        : Mono.error(ApiException.builder().code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                                .message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).status(HttpStatus.UNAUTHORIZED)
                                .build()))
                .flatMap(user -> {
                    return this.totpService.validate(user.getTwoFaSeed(), login.code()).map(isValid -> {
                        if (!isValid) {
                            user.setStatusLogin(UserLoginStatusEnum.PASSWORD.name());
                            userRepository.save(user);
                            throw ApiException.builder().code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                                    .message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).status(HttpStatus.UNAUTHORIZED)
                                    .build();
                        }
                        return user;
                    });
                })
                .map(user -> {
                    user.setStatusLogin(UserLoginStatusEnum.PASSWORD.name());
                    return userRepository.save(user);
                })
                .map(this.tokenService::generateToken);

    }

    @Override
    public Mono<String> refresh(String token) {
        return Mono.just(token).map(this.tokenService::validateToken)
                .flatMap(Mono::justOrEmpty)
                .map(userRepository::findById)
                .flatMap(Mono::justOrEmpty)
                .switchIfEmpty(Mono.error(ApiException.builder().code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).status(HttpStatus.UNAUTHORIZED).build()))
                .map(this.tokenService::generateToken);
    }

}
