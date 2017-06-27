package com.scmspain.infrastructure;

import com.scmspain.entities.Tweet;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class TweetRepositoryImp implements TweetRepository {
    private EntityManager entityManager;

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
        List<Tweet> result = new ArrayList<Tweet>();
        TypedQuery<Long> query = this.entityManager.createQuery("SELECT id FROM Tweet AS tweetId WHERE pre2015MigrationStatus<>99 and discarted=false ORDER BY id DESC", Long.class);
        List<Long> ids = query.getResultList();
        for (Long id : ids) {
            result.add(getTweet(id));
        }
        return result;
    }

    @Override
    public void discardTweet(long tweetId) {
        Tweet tweet = this.entityManager.find(Tweet.class, tweetId);
        tweet.setDiscarted(true);
        this.entityManager.persist(tweet);
    }
}
