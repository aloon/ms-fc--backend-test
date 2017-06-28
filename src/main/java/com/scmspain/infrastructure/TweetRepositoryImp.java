package com.scmspain.infrastructure;

import com.scmspain.entities.Tweet;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class TweetRepositoryImp implements TweetRepository {
    private final EntityManager entityManager;

    public TweetRepositoryImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Tweet tweet) {
        this.entityManager.persist(tweet);
    }

    @Override
    public Tweet getTweet(Long id) {
        return this.entityManager.find(Tweet.class, id);
    }

    @Override
    public List<Tweet> listAllTweets() {
        TypedQuery<Long> query = this.entityManager.createQuery("SELECT id FROM Tweet AS tweetId WHERE pre2015MigrationStatus<>99 and discarted=false ORDER BY creationDate DESC", Long.class);
        return query.getResultList()
                .stream()
                .map(this::getTweet)
                .collect(Collectors.toList());
    }

    @Override
    public void discardTweet(long tweetId) {
        Tweet tweet = this.entityManager.find(Tweet.class, tweetId);
        tweet.setDiscarted(true);
        this.entityManager.persist(tweet);
    }


    @Override
    public List<Tweet> listDiscardTweet() {
        TypedQuery<Long> query = this.entityManager.createQuery("SELECT id FROM Tweet AS tweetId WHERE pre2015MigrationStatus<>99 and discarted=true ORDER BY discardDate ", Long.class);
        return query.getResultList()
                .stream()
                .map(this::getTweet)
                .collect(Collectors.toList());
    }
}
