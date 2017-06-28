package com.scmspain.services;

import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

public class StatisticsServiceImp implements StatisticsService {
    private final MetricWriter metricWriter;

    public StatisticsServiceImp(MetricWriter metricWriter) {
        this.metricWriter = metricWriter;
    }

    @Override
    public void incrementPublishedTweets() {
        this.metricWriter.increment(new Delta<Number>("published-tweets", 1));
    }

    @Override
    public void incrementQueriedTweets() {
        this.metricWriter.increment(new Delta<Number>("times-queried-tweets", 1));
    }

    @Override
    public void incrementDiscardedTweets() {
        this.metricWriter.increment(new Delta<Number>("discarded-tweets", 1));
    }

    @Override
    public void incrementQueriedDiscardedTweets() {
        this.metricWriter.increment(new Delta<Number>("times-queried-discarded-tweets", 1));
    }
}
