package com.antaler.smlv.apis.users.services.users;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.antaler.smlv.apis.users.model.db.UserEntity;
import com.antaler.smlv.apis.users.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public final class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Mono<Void> create(final UserEntity user) {
        return Mono.just(user)
                .map(u -> userRepo.findFirstByEmail(user.getEmail()))
                .flatMap(u -> u.isPresent() 
                                ? Mono.error(new RuntimeException("User already exists"))
                                : Mono.just(user))
                .map(userRepo::save)
                .doOnNext(u -> log.info(u.toString()))
                .flatMap(u -> Mono.empty());

    }

}
