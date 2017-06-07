package com.diplom.notificator.subscription.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document(collection = "subscription")
public class Subscription {

    @Id
    private BigInteger id;

    @DBRef
    private User user;

    private String tag;

    private String email;

    public Subscription(String tag, String email) {
        this.tag = tag;
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", user=" + user +
                ", tag='" + tag + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
