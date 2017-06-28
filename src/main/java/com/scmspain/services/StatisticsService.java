package com.scmspain.services;

public interface StatisticsService {
    void incrementPublishedTweets();
    void incrementQueriedTweets();
    void incrementDiscartedTweets();
    void incrementQueriedDiscartedTweets();
}