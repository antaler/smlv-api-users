package com.antaler.smlv.apis.users.services.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.antaler.smlv.apis.users.model.Email;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class MailServiceImpl implements MailService {

    private JavaMailSender emailSender;

    @Override
    public void sendEmail(Email email) throws MessagingException {
        var mail = emailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mail, true);
        helper.setFrom(email.from());
        helper.setTo(email.to());
        helper.setSubject(email.subject());
        helper.setText(email.content(), true);

        emailSender.send(mail);
    }

}
