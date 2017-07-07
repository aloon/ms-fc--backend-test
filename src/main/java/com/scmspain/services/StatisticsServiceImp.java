package com.scmspain.services;

import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import java.util.concurrent.CompletableFuture;

public class StatisticsServiceImp implements StatisticsService {
    private final MetricWriter metricWriter;

    public StatisticsServiceImp(MetricWriter metricWriter) {
        this.metricWriter = metricWriter;
    }

    @Override
    public void incrementPublishedTweets() {
        incrementAsync("published-tweets", 1);
    }

    @Override
    public void incrementQueriedTweets() {
        incrementAsync("times-queried-tweets", 1);
    }

    @Override
    public void decreasePublishedTweets() {
        incrementAsync("published-tweets", -1);
    }

    @Override
    public void incrementQueriedDiscardedTweets() {
        incrementAsync("times-queried-discarded-tweets", 1);
    }

    private void incrementAsync(String key, int value) {
        CompletableFuture.runAsync(() -> increment(key, value, this.metricWriter));
    }

    private static void increment(String key, int value, MetricWriter metricWriter) {
        metricWriter.increment(new Delta<Number>(key, value));
    }

}
