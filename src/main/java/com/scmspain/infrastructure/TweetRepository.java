package com.scmspain.infrastructure;

import com.scmspain.entities.Tweet;

import java.util.List;

public interface TweetRepository {
    void save(Tweet tweet);

    Tweet getTweet(Long id);

    List<Tweet> listAllTweets();

    void discardTweet(long tweetId);

    List<Tweet> listDiscardTweet();
}
