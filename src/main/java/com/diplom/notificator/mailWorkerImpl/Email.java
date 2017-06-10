package com.diplom.notificator.mailWorkerImpl;


import com.google.cloud.vision.v1.Image;

import java.io.File;
import java.util.List;
import java.util.Set;

public class Email {

    private String to;

    private Set<String> tags;

    private File[] attachment;

    final String text = "По вашим запросам были найдены следующие объекты";

    final String from = "SubscriptionAPI";

    final String subject = "Ваши подписки";


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public File[] getAttachment() {
        return attachment;
    }

    public void setAttachment(File[] attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Email{" +
                "to='" + to + '\'' +
                ", tags=" + tags +
                '}';
    }
}
