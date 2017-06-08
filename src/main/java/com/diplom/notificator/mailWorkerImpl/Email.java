package com.diplom.notificator.mailWorkerImpl;


import java.util.Set;

public class Email {

    private String to;

    private Set<String> tags;

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

    @Override
    public String toString() {
        return "Email{" +
                "to='" + to + '\'' +
                ", tags=" + tags +
                '}';
    }
}
