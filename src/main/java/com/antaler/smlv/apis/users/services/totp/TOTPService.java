package com.antaler.smlv.apis.users.services.totp;

import com.antaler.smlv.apis.users.model.TOTPQrKey;

import reactor.core.publisher.Mono;

public sealed interface TOTPService permits TOTPServiceImpl {

    Mono<TOTPQrKey> generateKey(String user);

    Mono<Boolean> validate(String secretKey, String totp);
}
