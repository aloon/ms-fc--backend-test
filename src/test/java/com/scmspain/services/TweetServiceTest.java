package com.scmspain.services;

import com.scmspain.entities.Tweet;
import com.scmspain.infrastructure.TweetRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import javax.persistence.EntityManager;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TweetServiceTest {
    private MetricWriter metricWriter;
    private TweetService tweetService;
    private TweetTextService tweetTextService;
    private TweetRepository tweetRepository;

    @Before
    public void setUp() throws Exception {
        this.metricWriter = mock(MetricWriter.class);
        this.tweetTextService = new TweetTextServiceImp();
        this.tweetRepository = mock(TweetRepository.class);

        this.tweetService = new TweetServiceImp(metricWriter, tweetTextService, tweetRepository);
    }

    @Test
    public void shouldInsertANewTweet() throws Exception {
        tweetService.publishTweet("Guybrush Threepwood", "I am Guybrush Threepwood, mighty pirate.");
        verify(tweetRepository, times(1)).save(any());
    }

    @Test
    public void shouldDiscardANewTweet() throws Exception {
        tweetService.publishTweet("Guybrush Threepwood", "I am Guybrush Threepwood, mighty pirate.");
        final long tweetToDiscard = 123;
        tweetService.discardTweet(tweetToDiscard);
        verify(tweetRepository, times(1)).discardTweet(tweetToDiscard);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetLengthIsInvalid() throws Exception {
        tweetService.publishTweet("Pirate", "LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to drop dead. So he did. Then things really got ugly.");
    }
}
