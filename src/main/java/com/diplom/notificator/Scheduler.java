package com.diplom.notificator;

import com.diplom.notificator.googleCV.GoogleCVTagsAnalizer;
import com.diplom.notificator.imageWorker.ImageWorkerImpl;
import com.diplom.notificator.subscription.service.SubscriptionService;
import com.google.cloud.vision.v1.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@PropertySource("classpath:application.properties")
public class Scheduler {

    @Autowired
    private Environment environment;

    @Autowired
    private SubscriptionService subscriptionService;

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        GoogleCVTagsAnalizer analizer = new GoogleCVTagsAnalizer();
        List<Image> images = getImagesList();
        Set<String> tagsFromAllPictures = new HashSet<>();
        for (Image img : images) {
            List<String> tagsFromCurrentPicture = analizer.detectLabels(img);
            tagsFromAllPictures.addAll(tagsFromCurrentPicture);
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(tagsFromAllPictures);


//        try {
//            ImageWorker imageWorker = new GoogleCVTagsAnalizer();
//            List<String> tags = imageWorker.detectLabels("src/main/resources/pictures/porshe.jpg");
//            if (tags != null){
//                System.out.println(tags);
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
    }

    private List<Image> getImagesList(){
        String path = environment.getProperty("images.path");
        ImageWorkerImpl imgWorker = new ImageWorkerImpl();
        return imgWorker.getImageList(path);
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

}
