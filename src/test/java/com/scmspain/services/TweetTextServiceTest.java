package com.scmspain.services;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TweetTextServiceTest {

    private TweetTextService tweetTextService;
    private final String _140chars = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
    private final String _136chars = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456";
    private final String URL = "http://foogle.co";

    @Before
    public void setUp() {
        tweetTextService = new TweetTextServiceImp();
    }

    @Test
    public void valid_length() throws Exception {
        final String valid = "hola caracola";
        tweetTextService.setTweetText(valid);
        assertThat(tweetTextService.isValidLength()).isTrue();
    }

    @Test
    public void valid_length_exactly_140() throws Exception {
        tweetTextService.setTweetText(_140chars);
        assertThat(tweetTextService.isValidLength()).isTrue();
    }

    @Test
    public void invalid_length_141_chars() throws Exception {
        tweetTextService.setTweetText(_140chars + "A");
        assertThat(tweetTextService.isValidLength()).isFalse();
    }

    @Test
    public void valid_length_140_chars_and_url() throws Exception {
        tweetTextService.setTweetText(_140chars + URL);
        assertThat(tweetTextService.isValidLength()).isFalse();
    }

    @Test
    public void valid_length_136_chars_and_url() throws Exception {
        tweetTextService.setTweetText(_136chars + URL);
        assertThat(tweetTextService.isValidLength()).isTrue();
    }

    @Test
    public void valid_length_only_url() throws Exception {
        tweetTextService.setTweetText(URL);
        assertThat(tweetTextService.isValidLength()).isTrue();
    }

    @Test
    public void valid_length_url_of_more_than_140_chars() throws Exception {
        tweetTextService.setTweetText("url rara: http://www.coches.net/?q=" + _140chars);
        assertThat(tweetTextService.isValidLength()).isTrue();
    }


}
