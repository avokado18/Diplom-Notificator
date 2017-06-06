package com.diplom.notificator.subscription.service;

import com.diplom.notificator.subscription.entity.Subscription;
import com.diplom.notificator.subscription.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> findAll(){
        return subscriptionRepository.findAll();
    }

    public List<Subscription> findByTag(String tag){
        return subscriptionRepository.findByTag(tag);
    }
}
