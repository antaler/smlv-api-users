package com.antaler.smlv.apis.users.services.TokenService;

import java.util.Optional;

import com.antaler.smlv.apis.users.model.db.UserEntity;

public sealed interface TokenService permits TokenServiceImpl {
    /**
     * Generates a token for the given user.
     * 
     * @param user the user for which the token should be generated
     * @return token generated for the user
     */
    String generateToken(UserEntity user);

    Optional<String> validateToken(String token);
}
