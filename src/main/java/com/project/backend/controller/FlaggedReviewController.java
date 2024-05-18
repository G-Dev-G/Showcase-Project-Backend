package com.project.backend.controller;

import com.project.backend.dto.ReviewDto;
import com.project.backend.model.FlaggedReview;
import com.project.backend.service.FlaggedReviewService;
import com.project.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/flaggedReview")
public class FlaggedReviewController {
    private final FlaggedReviewService flaggedReviewService;
    private final ReviewService reviewService; // to get ReviewDto

    @Autowired
    public FlaggedReviewController(FlaggedReviewService flaggedReviewService, ReviewService reviewService) {
        this.flaggedReviewService = flaggedReviewService;
        this.reviewService = reviewService;
    }

    @GetMapping("/getReviewsFlaggedByUserIdAndProductId")
    public List<ReviewDto> getReviewsFlaggedByUserIdAndProductId(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        List<ReviewDto> reviewDtoList = new ArrayList<>();

        List<FlaggedReview> flaggedReviewList = flaggedReviewService.getFlaggedReviewsByUserIdAndProductId(userId, productId);
        for (FlaggedReview flaggedReview : flaggedReviewList) {
            reviewDtoList.add(reviewService.reviewEntityToDto(flaggedReview.getReview()));
        }
        return reviewDtoList;
    }

    @PutMapping("/toggleFlagByUserIdAndReviewId")
    public Boolean toggleFlagByUserIdAndReviewId(@RequestParam("userId") Long userId, @RequestParam("reviewId") Long reviewId) {
        FlaggedReview flaggedReview = flaggedReviewService.toggleFlagByUserIdAndReviewId(userId, reviewId);
        if (flaggedReview != null)
            return true; // success
        return false; // failed
    }
}
