package com.diplom.notificator.subscription.repository;

import com.diplom.notificator.subscription.entity.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, BigInteger> {

    List<Subscription> findByTag(String tag);

    @Query(fields = "{ 'email' : 0, 'user' : 0, '_id' : 0 }")
    List<Subscription> findByEmail(String email);
}
