package com.scmspain.controller;

import com.scmspain.controller.command.DiscardTweetCommand;
import com.scmspain.controller.command.PublishTweetCommand;
import com.scmspain.entities.Tweet;
import com.scmspain.services.TweetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class TweetController {
    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/tweet")
    public List<Tweet> listAllTweets() {
        return this.tweetService.listAllTweets();
    }

    @PostMapping("/tweet")
    @ResponseStatus(CREATED)
    public void publishTweet(@RequestBody PublishTweetCommand publishTweetCommand) {
        this.tweetService.publishTweet(publishTweetCommand.getPublisher(), publishTweetCommand.getTweet());
    }

    @PostMapping("/discarded")
    @ResponseStatus(CREATED)
    public void dicardTweet(@RequestBody DiscardTweetCommand discardTweetCommand) {
        this.tweetService.discardTweet(discardTweetCommand.getTweet());
    }

    @GetMapping("/discarded")
    public List<Tweet> listDicardTweet() {
        return tweetService.listDiscardTweet();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Object invalidArgumentException(IllegalArgumentException ex) {
        return new Object() {
            public String message = ex.getMessage();
            public String exceptionClass = ex.getClass().getSimpleName();
        };
    }
}
