package com.scmspain.infrastructure;

import com.scmspain.entities.Tweet;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class TweetRepositoryImp implements TweetRepository {
    private final EntityManager entityManager;

    public TweetRepositoryImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Tweet tweet) {
        this.entityManager.persist(tweet);
    }

    private Tweet getTweet(Long id) {
        return this.entityManager.find(Tweet.class, id);
    }

    @Override
    public List<Tweet> listAllTweets() {
        return listTweet(false);
    }

    @Override
    public void discardTweet(long tweetId) {
        Tweet tweet = getTweet(tweetId);
        tweet.setDiscarded(true);
        tweet.setDiscardDate(new Date());
        this.entityManager.persist(tweet);
    }

    @Override
    public List<Tweet> listDiscardTweet() {
        return listTweet(true);
    }

    private List<Tweet> listTweet(boolean discarded) {
        final String order = discarded ? "discardDate" : "creationDate";
        final String jql = "FROM Tweet WHERE pre2015MigrationStatus<>99 and discarded=:discarded ORDER BY " + order;

        return this.entityManager
                .createQuery(jql, Tweet.class)
                .setParameter("discarded", discarded)
                .getResultList();
    }

}
