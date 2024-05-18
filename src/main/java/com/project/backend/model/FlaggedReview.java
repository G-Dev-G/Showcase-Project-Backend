package com.project.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "FlaggedReview")
public class FlaggedReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long flaggedReviewId; // primary key

    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user; // foreign key 1

    @ManyToOne()
    @JoinColumn(name = "reviewId", referencedColumnName = "reviewId")
    private Review review; // foreign key 2

    public FlaggedReview() {}

    public FlaggedReview(Long flaggedReviewId, User user, Review review) {
        this.flaggedReviewId = flaggedReviewId;
        this.user = user;
        this.review = review;
    }

    public Long getFlaggedReviewId() {
        return flaggedReviewId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
