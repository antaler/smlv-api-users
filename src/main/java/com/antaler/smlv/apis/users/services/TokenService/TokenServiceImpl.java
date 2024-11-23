package com.antaler.smlv.apis.users.services.TokenService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.antaler.smlv.apis.users.model.db.UserEntity;
import com.antaler.smlv.apis.users.properties.AppProperties;
import com.antaler.smlv.apis.users.properties.token.TokenProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public final class TokenServiceImpl implements TokenService {


    private final Algorithm alg;

    private final TokenProperties props;

    public TokenServiceImpl(Algorithm algorithmRSA256, AppProperties props) {
        this.alg = algorithmRSA256;
        this.props = props.getToken();
    }

    @Override
    public String generateToken(UserEntity user) {
        var expiresAt = Instant.now().toEpochMilli() + props.getExpiresSeconds() * 1000;
        return JWT.create()
        .withIssuer(props.getIssuer())
        .withSubject(user.getId())
        .withIssuedAt(Instant.now())
        .withExpiresAt(Instant.ofEpochMilli(expiresAt))
        .sign(this.alg);
    }

    @Override
    public Optional<String> validateToken(String token) {
        try {

            var decodedToken = JWT.require(alg).withIssuer(props.getIssuer()).acceptExpiresAt(0).build().verify(token);
            return Optional.of(decodedToken.getSubject());
        }catch (JWTVerificationException exception){
            log.info("Token is not valid: {}", exception.getMessage());
            return Optional.empty();
        }
        
    }


    





}
