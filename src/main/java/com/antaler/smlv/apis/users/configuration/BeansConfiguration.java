package com.antaler.smlv.apis.users.configuration;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.antaler.smlv.apis.users.properties.AppProperties;
import com.antaler.smlv.apis.users.services.mail.MailService;
import com.antaler.smlv.apis.users.services.mail.MailServiceDisableImpl;
import com.antaler.smlv.apis.users.services.mail.MailServiceImpl;
import com.auth0.jwt.algorithms.Algorithm;

@Configuration
public class BeansConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "app-smlv.mail", name = "enabled", matchIfMissing = false, havingValue = "true")
    static MailService mailService(JavaMailSender emailSender) {
        return new MailServiceImpl(emailSender);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app-smlv.mail", name = "enabled", matchIfMissing = false, havingValue = "false")
    static MailService mailServiceDisable() {
        return new MailServiceDisableImpl();
    }

    @Bean
    static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    static Algorithm algorithmRSA256(AppProperties  props) throws NoSuchAlgorithmException, InvalidKeySpecException {
        
        KeyFactory kf = KeyFactory.getInstance("RSA");
        var privateKey = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(props.getKeys().getPriv()));
        var publicKey =  new X509EncodedKeySpec(Base64.getDecoder().decode(props.getKeys().getPub()));

        var pvKey = kf.generatePrivate(privateKey);
        var pubKey = kf.generatePublic(publicKey);
        return Algorithm.RSA256((RSAPublicKey) pubKey, (RSAPrivateKey) pvKey);
    }

}
