package com.scmspain.services;

import com.scmspain.infrastructure.TweetRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TweetServiceTest {
    private TweetService tweetService;
    private TweetTextService tweetTextService;
    private TweetRepository tweetRepository;
    private StatisticsService statisticsService;

    @Before
    public void setUp() throws Exception {
        this.tweetTextService = new TweetTextServiceImp();
        this.tweetRepository = mock(TweetRepository.class);
        this.statisticsService = mock(StatisticsService.class);

        this.tweetService = new TweetServiceImp(tweetTextService, tweetRepository, statisticsService);
    }

    @Test
    public void shouldInsertANewTweet() throws Exception {
        tweetService.publishTweet("Guybrush Threepwood", "I am Guybrush Threepwood, mighty pirate.");
        verify(tweetRepository, times(1)).save(any());
        verify(statisticsService, times(1)).incrementPublishedTweets();
    }

    @Test
    public void shouldDiscardANewTweet() throws Exception {
        tweetService.publishTweet("Guybrush Threepwood", "I am Guybrush Threepwood, mighty pirate.");
        final long tweetToDiscard = 123;
        tweetService.discardTweet(tweetToDiscard);
        verify(tweetRepository, times(1)).discardTweet(tweetToDiscard);
        verify(statisticsService, times(1)).incrementDiscardedTweets();
    }

    @Test
    public void shouldListDiscardTweets() throws Exception {
        tweetService.listDiscardTweet();
        verify(tweetRepository, times(1)).listDiscardTweet();
        verify(statisticsService, times(1)).incrementQueriedDiscardedTweets();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetLengthIsInvalid() throws Exception {
        tweetService.publishTweet("Pirate", "LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to drop dead. So he did. Then things really got ugly.");

        verify(statisticsService, never()).incrementPublishedTweets();
    }
}
