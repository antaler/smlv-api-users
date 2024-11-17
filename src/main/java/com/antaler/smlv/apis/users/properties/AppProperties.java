package com.antaler.smlv.apis.users.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.antaler.smlv.apis.users.properties.key.KeysProperties;
import com.antaler.smlv.apis.users.properties.mail.EmailProperties;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(ignoreUnknownFields = false, ignoreInvalidFields = false, prefix = "app-smlv")
public class AppProperties {

    private EmailProperties mail;

    private KeysProperties keys;

}
