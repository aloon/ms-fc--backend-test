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
        incrementAsync("published-tweets");
    }

    @Override
    public void incrementQueriedTweets() {
        incrementAsync("times-queried-tweets");
    }

    @Override
    public void incrementDiscardedTweets() {
        incrementAsync("discarded-tweets");
    }

    @Override
    public void incrementQueriedDiscardedTweets() {
        incrementAsync("times-queried-discarded-tweets");
    }

    private void incrementAsync(String key) {
        CompletableFuture.runAsync(() -> increment(key, this.metricWriter));
    }

    private static void increment(String key, MetricWriter metricWriter) {
        metricWriter.increment(new Delta<Number>(key, 1));
    }

}
