package com.scmspain.services;

import com.scmspain.entities.Tweet;
import com.scmspain.infrastructure.TweetRepository;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TweetServiceImp implements TweetService {
    private EntityManager entityManager;
    private MetricWriter metricWriter;
    private TweetTextService tweetTextService;
    private TweetRepository tweetRepository;

    public TweetServiceImp(MetricWriter metricWriter,
                           TweetTextService tweetTextService,
                           TweetRepository tweetRepository) {
        this.entityManager = entityManager;
        this.metricWriter = metricWriter;
        this.tweetTextService = tweetTextService;
        this.tweetRepository = tweetRepository;
    }

    /**
     * Push tweet to repository
     * Parameter - publisher - creator of the Tweet
     * Parameter - text - Content of the Tweet
     * Result - recovered Tweet
     */
    public void publishTweet(String publisher, String text) {
        tweetTextService.setTweetText(text);
        if (publisher != null && publisher.length() > 0 && tweetTextService.isValidLength()) {
            Tweet tweet = new Tweet();
            tweet.setTweet(text);
            tweet.setPublisher(publisher);

            this.metricWriter.increment(new Delta<Number>("published-tweets", 1));
            //this.entityManager.persist(tweet);
            tweetRepository.save(tweet);
        } else {
            throw new IllegalArgumentException("Tweet must not be greater than 140 characters");
        }
    }

    /**
     * Recover tweet from repository
     * Parameter - id - id of the Tweet to retrieve
     * Result - retrieved Tweet
     */
    public Tweet getTweet(Long id) {
        return tweetRepository.getTweet(id);
    }

    /**
     * Recover tweet from repository
     * Parameter - id - id of the Tweet to retrieve
     * Result - retrieved Tweet
     */
    public List<Tweet> listAllTweets() {
        this.metricWriter.increment(new Delta<Number>("times-queried-tweets", 1));

        return tweetRepository.listAllTweets();
    }

    public void discardTweet(long tweetId) {
        tweetRepository.discardTweet(tweetId);
    }
}
