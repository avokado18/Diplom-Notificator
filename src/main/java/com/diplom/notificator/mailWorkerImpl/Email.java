package com.diplom.notificator.mailWorkerImpl;


import java.util.Set;

public class Email {

    private String to;

    private Set<String> tasg;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Set<String> getTasg() {
        return tasg;
    }

    public void setTasg(Set<String> tasg) {
        this.tasg = tasg;
    }

    @Override
    public String toString() {
        return "Email{" +
                "to='" + to + '\'' +
                ", tasg=" + tasg +
                '}';
    }
}
