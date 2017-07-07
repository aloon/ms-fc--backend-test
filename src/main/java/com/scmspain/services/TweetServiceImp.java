package com.scmspain.services;

import com.scmspain.entities.Tweet;
import com.scmspain.infrastructure.TweetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TweetServiceImp implements TweetService {

    private final TweetTextService tweetTextService;
    private final TweetRepository tweetRepository;
    private final StatisticsService statisticsService;

    public TweetServiceImp(TweetTextService tweetTextService,
                           TweetRepository tweetRepository,
                           StatisticsService statisticsService) {
        this.tweetTextService = tweetTextService;
        this.tweetRepository = tweetRepository;
        this.statisticsService = statisticsService;
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
            tweet.setCreationDate(new Date());
            tweet.setPublisher(publisher);

            tweetRepository.save(tweet);
            statisticsService.incrementPublishedTweets();
        } else {
            throw new IllegalArgumentException("Tweet must not be greater than 140 characters");
        }
    }

    /**
     * Recover tweet from repository
     * Parameter - id - id of the Tweet to retrieve
     * Result - retrieved Tweet
     */
    public List<Tweet> listAllTweets() {
        statisticsService.incrementQueriedTweets();
        return tweetRepository.listAllTweets();
    }

    public void discardTweet(long tweetId) {
        statisticsService.incrementDiscardedTweets();
        tweetRepository.discardTweet(tweetId);
    }

    @Override
    public List<Tweet> listDiscardTweet() {
        statisticsService.incrementQueriedDiscardedTweets();
        return tweetRepository.listDiscardTweet();
    }
}
