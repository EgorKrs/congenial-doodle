package com.loneliness.service;

import com.loneliness.entity.domain.Orders;
import com.loneliness.entity.domain.Review;
import com.loneliness.repository.OrderRepository;
import com.loneliness.repository.ReviewRepository;
import com.loneliness.util.search.Searcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ReviewService  extends CRUDService<Review>  {
    public ReviewService(ReviewRepository repository, Searcher searcher){
        this.repository=repository;
        this.searcher=searcher;
    }

}
