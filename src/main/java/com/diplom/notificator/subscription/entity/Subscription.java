package com.diplom.notificator.subscription.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document(collection = "subscription")
public class Subscription {

    @Id
    private BigInteger id;

    private String tag;

    private String email;

    public Subscription(String tag, String email) {
        this.tag = tag;
        this.email = email;
    }

    public String getTag() {
        return tag;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
