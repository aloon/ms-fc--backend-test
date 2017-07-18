package com.scmspain.infrastructure;

import com.scmspain.entities.Tweet;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TweetRepositoryImpTest {
    private EntityManager entityManager;
    private Tweet simpleTweet;

    @Before
    public void setUp() throws Exception {
        this.entityManager = mock(EntityManager.class);
    }

    @Test
    public void shouldSave() {
        new TweetRepositoryImp(this.entityManager).save(any());
        verify(entityManager, times(1)).persist(any());
    }

    @Test
    public void shouldListAllTweets() {
        List<Tweet> tweets = new TweetRepositoryImpFake(this.entityManager).listAllTweets();
        assertThat(tweets.get(0).getTweet(), is("tweet"));
    }

    @Test
    public void shouldDiscardTweet() {
        Long tweetToDiscard = 1L;
        simpleTweet = new Tweet();
        simpleTweet.setTweet("tweet");
        simpleTweet.setDiscarded(true);
        simpleTweet.setId(tweetToDiscard);

        new TweetRepositoryImpFake(this.entityManager).discardTweet(tweetToDiscard);
        verify(entityManager, times(1)).persist(simpleTweet);
    }

    @Test
    public void shouldListDiscardTweet() {
        List<Tweet> tweets = new TweetRepositoryImpFake(this.entityManager).listAllTweets();
        assertThat(tweets.get(0).getTweet(), is("tweet"));
    }


    private class TweetRepositoryImpFake extends TweetRepositoryImp {

        public TweetRepositoryImpFake(EntityManager entityManager) {
            super(entityManager);
        }

        @Override
        protected List<Tweet> listTweets(boolean discarded) {
            Tweet tweet = new Tweet();
            tweet.setTweet(discarded ? "discarded tweet" : "tweet");
            return Arrays.asList(tweet);
        }

        @Override
        protected Tweet getTweet(Long id) {
            return simpleTweet;
        }

    }
}