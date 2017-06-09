package com.diplom.notificator;

import com.diplom.notificator.googleCV.GoogleCVTagsAnalizer;
import com.diplom.notificator.imageWorker.ImageWorker;
import com.diplom.notificator.imageWorker.ImageWorkerImpl;
import com.diplom.notificator.mailWorkerImpl.Email;
import com.diplom.notificator.mailWorkerImpl.JavaEmailSender;
import com.diplom.notificator.subscription.service.SubscriptionService;
import com.google.cloud.vision.v1.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@PropertySource("classpath:application.properties")
public class Scheduler {

    @Autowired
    private JavaEmailSender javaEmailSender;

    @Autowired
    private Environment environment;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ImageWorker imageWorker;

    @Scheduled(fixedRate = 60000)
    public void task() {
        sendEmails();
    }

    private List<Image> getImagesList(){
        String path = environment.getProperty("images.path");
        return imageWorker.getImageList(path);
    }


    private Map<String, Set<String>> getEmailTagsMap(){
        Set<String> emails = subscriptionService.findEmails();
        Map<String, Set<String>> emailAndTagsFromSubscriptions = new HashMap<>();
        for (String email : emails) {
            Set<String> tagsForEmail = subscriptionService.findTagsByEmail(email);
            emailAndTagsFromSubscriptions.put(email, tagsForEmail);
        }
        return emailAndTagsFromSubscriptions;
    }

    private List<Email> getListEmailsToSend(){
        GoogleCVTagsAnalizer analizer = new GoogleCVTagsAnalizer();
        List<Image> images = getImagesList();
        Map<String, Set<String>> emailTagsSubscr = getEmailTagsMap();
        System.out.println(emailTagsSubscr);
        Set<String> tagsFromAllPictures = new HashSet<>(analizer.detectLabels(images));
        System.out.println(tagsFromAllPictures);
        List<Email> emails = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : emailTagsSubscr.entrySet()) {
            entry.getValue().retainAll(tagsFromAllPictures);
            Email email = new Email();
            email.setTo(entry.getKey());
            email.setTasg(entry.getValue());
            emails.add(email);
        }
        return emails;
    }

    private void sendEmails(){
        List<Email> emails = getListEmailsToSend();
        for (Email email : emails) {
            javaEmailSender.send(email);
        }
    }

}
