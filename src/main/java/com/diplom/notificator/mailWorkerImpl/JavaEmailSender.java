package com.diplom.notificator.mailWorkerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

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
        mail.setText(email.text + email.getTags().toString());

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mail.getFrom());
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getText());
            for (File file : email.getAttachment()){
                helper.addAttachment(file.getName(), file);
            }
        }
        catch (MessagingException ex) {
            ex.printStackTrace();
        }
        javaMailSender.send(message);
    }
}
