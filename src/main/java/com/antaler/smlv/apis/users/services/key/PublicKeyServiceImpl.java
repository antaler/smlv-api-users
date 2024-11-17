package com.antaler.smlv.apis.users.services.key;

import org.springframework.stereotype.Service;

import com.antaler.smlv.apis.users.properties.AppProperties;

import lombok.Data;
import reactor.core.publisher.Mono;

@Data
@Service
public final  class PublicKeyServiceImpl implements PublicKeyService {

    
    private final String publicKey;

    public PublicKeyServiceImpl(AppProperties props){
        this.publicKey = props.getKeys().getPub();
    }


    public Mono<String> getPublicKey(){
        return Mono.just(publicKey);
    }

}
