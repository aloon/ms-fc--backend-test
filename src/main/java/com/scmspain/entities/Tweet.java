package com.scmspain.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Tweet {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String publisher;
    @Column(nullable = false, length = 2000)
    private String tweet;
    @Column()
    private Long pre2015MigrationStatus = 0L;
    @Column(nullable = false)
    private boolean discarded = false;
    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    private Date discardDate;

    public Tweet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Long getPre2015MigrationStatus() {
        return pre2015MigrationStatus;
    }

    public void setPre2015MigrationStatus(Long pre2015MigrationStatus) {
        this.pre2015MigrationStatus = pre2015MigrationStatus;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDiscardDate() {
        return discardDate;
    }

    public void setDiscardDate(Date discardDate) {
        this.discardDate = discardDate;
    }
}
