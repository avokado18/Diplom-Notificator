package com.diplom.notificator.subscription.service;

import com.diplom.notificator.subscription.entity.Subscription;
import com.diplom.notificator.subscription.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> findByTag(String tag){
        return subscriptionRepository.findByTag(tag);
    }

    public Set<String> findTagsByEmail(String email){
        List<Subscription> subscriptions = subscriptionRepository.findByEmail(email);
        Set<String> tags = new HashSet<>();
        for (Subscription subscription : subscriptions){
            tags.add(subscription.getTag());
        }
        return tags;
    }

    public Set<String> findEmails(){
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        Set<String> emails = new HashSet<>();
        for (Subscription subscription : subscriptions){
            emails.add(subscription.getEmail());
        }
        return emails;
    }
}
