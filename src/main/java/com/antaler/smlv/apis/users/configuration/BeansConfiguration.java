package com.antaler.smlv.apis.users.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.antaler.smlv.apis.users.services.mail.MailService;
import com.antaler.smlv.apis.users.services.mail.MailServiceDisableImpl;
import com.antaler.smlv.apis.users.services.mail.MailServiceImpl;

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

}
