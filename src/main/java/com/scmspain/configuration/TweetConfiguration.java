package com.scmspain.configuration;

import com.scmspain.controller.TweetController;
import com.scmspain.infrastructure.TweetRepository;
import com.scmspain.infrastructure.TweetRepositoryImp;
import com.scmspain.services.*;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class TweetConfiguration {
    @Bean
    public TweetService getTweetService(TweetTextService tweetTextService, TweetRepository tweetRepository, StatisticsService statisticsService) {
        return new TweetServiceImp(tweetTextService, tweetRepository, statisticsService);
    }

    @Bean
    public TweetTextService getTweetTextService() {
        return new TweetTextServiceImp();
    }

    @Bean
    public TweetController getTweetConfiguration(TweetService tweetService) {
        return new TweetController(tweetService);
    }

    @Bean
    public TweetRepository getTweetRepository(EntityManager entityManager){
        return new TweetRepositoryImp(entityManager);
    }

    @Bean
    public StatisticsService getStatisticsService(MetricWriter metricWriter){
        return new StatisticsServiceImp(metricWriter);
    }
}
