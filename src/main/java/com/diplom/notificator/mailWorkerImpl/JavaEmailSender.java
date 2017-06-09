package com.diplom.notificator.mailWorkerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class JavaEmailSender {

    private JavaMailSender javaMailSender;

    @Autowired
    public JavaEmailSender(JavaMailSender javaMailSender){
        this.javaMailSender =javaMailSender;
    }


    public void send(Email email) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email.getTo());
        mail.setFrom(email.from);
        mail.setSubject(email.subject);
        mail.setText(email.text + email.getTasg().toString());
        javaMailSender.send(mail);
    }
}
