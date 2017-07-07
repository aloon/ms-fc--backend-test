package com.scmspain.services;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class TweetTextServiceImp implements TweetTextService {
    private String tweetText;

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public boolean isValidLength() {
        final int MAX_TWEET_LENGTH = 140;
        if (tweetText == null || tweetText.isEmpty()) {
            return false;
        } else {
            final String regexUrl = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            Pattern p = Pattern.compile(regexUrl);
            Matcher m = p.matcher(tweetText);
            if (m.find()) {
                return m.replaceAll("").length() <= (MAX_TWEET_LENGTH - m.groupCount());
            } else {
                return tweetText.length() <= MAX_TWEET_LENGTH;
            }
        }
    }
}