package com.antaler.smlv.apis.users.properties.token;

import lombok.Data;

@Data
public class TokenProperties {

    private String issuer;

    private long expiresSeconds;
}
