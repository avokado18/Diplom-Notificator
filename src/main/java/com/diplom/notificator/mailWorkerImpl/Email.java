package com.diplom.notificator.mailWorkerImpl;


import java.util.Set;

public class Email {

    private String to;

    private Set<String> tasg;

    final String text = "По вашим запросам были найдены следующие объекты";

    final String from = "SubscriptionAPI";

    final String subject = "Ваши подписки";

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
