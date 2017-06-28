package com.scmspain.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scmspain.configuration.TestConfiguration;
import com.scmspain.entities.Tweet;
import com.scmspain.services.TweetTextService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class TweetControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(this.context).build();
        //clearData();
    }

    @Test
    public void shouldReturn200WhenInsertingAValidTweet() throws Exception {
        mockMvc.perform(newTweet("Prospect", "Breaking the law"))
                .andExpect(status().is(201));
    }

    @Test
    public void shouldReturn200WhenInsertingAValidTweetWithURLOvercoming140Chars() throws Exception {
        mockMvc.perform(newTweet("Schibsted Spain", "We are Schibsted Spain (look at our home page http://www.schibsted.es/), we own Vibbo, InfoJobs, fotocasa, coches.net and milanuncios. Welcome!"))
                .andExpect(status().is(201));
    }

    @Test
    public void shouldReturn400WhenInsertingAnUnvalidTweet() throws Exception {
        mockMvc.perform(newTweet("Schibsted Spain", "We are Schibsted Spain (look at our home page ESTO_NO_ES_UNA_URL sdgsdfhsdf), we own Vibbo, InfoJobs, fotocasa, coches.net and milanuncios. Welcome!"))
                .andExpect(status().is(400));
    }

    @Test
    public void shouldReturnAllPublishedTweets() throws Exception {
        mockMvc.perform(newTweet("Yo", "How are you?"))
                .andExpect(status().is(201));

        MvcResult getResult = mockMvc.perform(get("/tweet"))
                .andExpect(status().is(200))
                .andReturn();

        String content = getResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(content, List.class).size()).isEqualTo(1);
    }

    //@Test
    public void each_test_is_indipendent() throws Exception {
        mockMvc.perform(newTweet("Yo", "How are you?"))
                .andExpect(status().is(201));

        MvcResult getResult = mockMvc.perform(get("/tweet"))
                .andExpect(status().is(200))
                .andReturn();

        String content = getResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(content, List.class).size()).isEqualTo(1);
    }

    @Test
    public void discardTweet() throws Exception {

        mockMvc.perform(newTweet("Prospect", "Breaking the law"))
                .andExpect(status().is(201));

        long tweetToDiscard = getTweetsFromList().get(0).getId();

        mockMvc.perform(discardTweet(tweetToDiscard))
                .andExpect(status().is(201));

        assertThat(getTweetsFromList().stream().filter(t -> t.getId() == tweetToDiscard).count()).isEqualTo(0);
    }

    @Test
    public void getDiscardTweets() throws Exception {

        mockMvc.perform(newTweet("Prospect", "Breaking the law"))
                .andExpect(status().is(201));

        long tweetToDiscard = getTweetsFromList().get(0).getId();

        mockMvc.perform(discardTweet(tweetToDiscard))
                .andExpect(status().is(201));

        mockMvc.perform(listDiscardTweets())
                .andExpect(status().is(200));

        assertThat(getDiscardTweetsFromList().stream().filter(t -> t.getId() == tweetToDiscard).count()).isEqualTo(1);
    }

    private List<Tweet> getTweetsFromList() throws Exception {
        MvcResult getResult = mockMvc.perform(get("/tweet"))
                .andExpect(status().is(200))
                .andReturn();
        String content = getResult.getResponse().getContentAsString();
        return new ObjectMapper().readValue(content, new TypeReference<List<Tweet>>() {
        });
    }

    private List<Tweet> getDiscardTweetsFromList() throws Exception {
        MvcResult getResult = mockMvc.perform(get("/discarded"))
                .andExpect(status().is(200))
                .andReturn();
        String content = getResult.getResponse().getContentAsString();
        return new ObjectMapper().readValue(content, new TypeReference<List<Tweet>>() {
        });
    }

    private MockHttpServletRequestBuilder newTweet(String publisher, String tweet) {
        return post("/tweet")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(format("{\"publisher\": \"%s\", \"tweet\": \"%s\"}", publisher, tweet));
    }

    private MockHttpServletRequestBuilder discardTweet(long tweetId) {
        return post("/discarded")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(format("{\"tweet\": \"%s\"}", tweetId));
    }

    private MockHttpServletRequestBuilder listDiscardTweets() {
        return get("/discarded");
    }

}
