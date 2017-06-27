package com.scmspain.services;

public interface TweetTextService {
    String getTweetText();

    void setTweetText(String tweetText);

    boolean isValidLength();
}
