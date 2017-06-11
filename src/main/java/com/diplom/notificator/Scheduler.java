package com.diplom.notificator;

import com.diplom.notificator.googleCV.GoogleCVTagsAnalizer;
import com.diplom.notificator.imageWorker.ImageWorker;
import com.diplom.notificator.mailWorkerImpl.Email;
import com.diplom.notificator.mailWorkerImpl.JavaEmailSender;
import com.diplom.notificator.subscription.service.SubscriptionService;
import com.google.cloud.vision.v1.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
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

    @Autowired
    private GoogleCVTagsAnalizer analizer;

    @Scheduled(fixedRate = 60000)
    public void task() {
        sendEmails();
    }

    private List<Image> getImagesList(){
        String path = environment.getProperty("images.path");
        return imageWorker.getImageList(path);
    }

    private File[] getFilesList(){
        String path = environment.getProperty("images.path");
        File myFolder = new File(path);
        return myFolder.listFiles();
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
        File[] imgs = getFilesList();
        Map<Integer, Set<String>> pictureTagsMap = analizer.detectLabels(getImagesList());
        Map<String, Set<String>> emailAndTagsFromSubscriptions = getEmailTagsMap();
        System.out.println(emailAndTagsFromSubscriptions);
        Set<String> allTagsFromPictures = getAllTagsFromPictures(pictureTagsMap);
        System.out.println(allTagsFromPictures);
        List<Email> emails = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : emailAndTagsFromSubscriptions.entrySet()) {
            entry.getValue().retainAll(allTagsFromPictures);
            if (!entry.getValue().isEmpty()){
                List<File> filesToSend = new ArrayList<>();
                for(Map.Entry<Integer, Set<String>> pictureTag : pictureTagsMap.entrySet()){
                    boolean contain = false;
                    for (String tag : entry.getValue()){
                        if (pictureTag.getValue().contains(tag)){
                            contain = true;
                        }
                    }
                    if (contain){
                        filesToSend.add(imgs[pictureTag.getKey()]);
                    }
                }
                Email email = new Email();
                email.setTo(entry.getKey());
                email.setTags(entry.getValue());
                email.setAttachment(filesToSend);
                emails.add(email);
                System.out.println(email);
            }
        }
        return emails;
    }

    private void sendEmails(){
        List<Email> emails = getListEmailsToSend();
        for (Email email : emails) {
            javaEmailSender.send(email);
        }
    }

    private Set<String> getAllTagsFromPictures(Map<Integer, Set<String>> tags){
        Set<String> result = new HashSet<>();
        for (int i = 0; i < tags.size(); i++){
            result.addAll(tags.get(i));
        }
        return result;
    }

}
