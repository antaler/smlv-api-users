package com.antaler.smlv.apis.users.services.key;

import reactor.core.publisher.Mono;

public sealed interface PublicKeyService permits PublicKeyServiceImpl  {

    Mono<String> getPublicKey();

}
