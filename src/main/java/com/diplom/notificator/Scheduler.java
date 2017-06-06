package com.diplom.notificator;

import com.diplom.notificator.imageWorker.ImageWorker;
import com.diplom.notificator.imageWorkerImpl.GoogleCVImageWorker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class Scheduler {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        System.out.println("The time is now {}" + dateFormat.format(new Date()));
        try {
            ImageWorker imageWorker = new GoogleCVImageWorker();
            List<String> tags = imageWorker.detectLabels("src/main/resources/porshe.jpg");
            if (tags != null){
                System.out.println(tags);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
