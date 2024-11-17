package com.antaler.smlv.apis.users.services.mail;

import com.antaler.smlv.apis.users.model.Email;

import jakarta.mail.MessagingException;

public sealed interface MailService permits MailServiceImpl, MailServiceDisableImpl{


    void sendEmail(Email email) throws MessagingException;

}
