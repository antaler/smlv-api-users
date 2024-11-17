package com.antaler.smlv.apis.users.services.mail;

import com.antaler.smlv.apis.users.model.Email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class MailServiceDisableImpl implements MailService{

    @Override
    public void sendEmail(Email email) {
        log.trace("Email: {}", email.toString());
    }


}
