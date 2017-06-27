package com.scmspain.configuration;

import com.scmspain.controller.TweetController;
import com.scmspain.services.TweetService;
import com.scmspain.services.TweetTextService;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class TweetConfiguration {
    @Bean
    public TweetService getTweetService(EntityManager entityManager, MetricWriter metricWriter, TweetTextService tweetTextService) {
        return new TweetService(entityManager, metricWriter, tweetTextService);
    }

    @Bean
    public TweetTextService getTweetTextService() {
        return new TweetTextService();
    }

    @Bean
    public TweetController getTweetConfiguration(TweetService tweetService) {
        return new TweetController(tweetService);
    }
}
