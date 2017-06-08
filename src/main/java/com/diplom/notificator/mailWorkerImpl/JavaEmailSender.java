package com.diplom.notificator.mailWorkerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@EnableAutoConfiguration
@Component
public class JavaEmailSender {

    private JavaMailSender javaMailSender;

    @Autowired
    public JavaEmailSender(JavaMailSender javaMailSender){
        this.javaMailSender =javaMailSender;
    }

    private final String text = "По вашим запросам были найдены следующие объекты";

    private final String from = "SubscriptionAPI";

    private final String subject = "Ваши подписки";

    public void send(Email email) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email.getTo());
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.setText(text + email.getTasg().toString());
        javaMailSender.send(mail);
    }
}
