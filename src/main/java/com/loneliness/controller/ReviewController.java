package com.loneliness.controller;

import com.loneliness.dto.ReviewDTO;
import com.loneliness.entity.domain.Review;
import com.loneliness.service.ReviewService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("review")
public class ReviewController extends CommonController<Review, ReviewDTO>{


    public ReviewController(ReviewService reviewService) {
        this.service = reviewService;
    }


}
