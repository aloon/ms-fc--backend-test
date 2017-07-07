package com.scmspain.services;

public interface StatisticsService {
    void incrementPublishedTweets();
    void incrementQueriedTweets();
    void decreasePublishedTweets();
    void incrementQueriedDiscardedTweets();
}