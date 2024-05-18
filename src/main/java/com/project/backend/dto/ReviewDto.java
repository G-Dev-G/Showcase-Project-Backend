package com.project.backend.dto;

import java.util.Date;

public class ReviewDto {
    private Long reviewId;
    private Long orderItemId;
    private Integer rating;
    private String comment;
    private Date reviewDate;
    /** added property for Frontend **/
    private String userName;
    private Integer flaggedCount;

    public ReviewDto() {}

    public ReviewDto(Long reviewId, Long orderItemId, Integer rating, String comment, Date reviewDate, String userName, Integer flaggedCount) {
        this.reviewId = reviewId;
        this.orderItemId = orderItemId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.userName = userName;
        this.flaggedCount = flaggedCount;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getFlaggedCount() {
        return flaggedCount;
    }

    public void setFlaggedCount(Integer flaggedCount) {
        this.flaggedCount = flaggedCount;
    }
}
