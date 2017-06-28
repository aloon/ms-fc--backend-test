package com.scmspain.services;

import com.scmspain.entities.Tweet;

import java.util.List;

public interface TweetService {
    void publishTweet(String publisher, String text);

    List<Tweet> listAllTweets();

    void discardTweet(long tweetId);

    List<Tweet> listDiscardTweet();
}
