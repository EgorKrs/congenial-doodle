package com.loneliness.service;

import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Orders;
import com.loneliness.repository.BookRepository;
import com.loneliness.repository.OrderRepository;
import com.loneliness.util.search.Searcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class OrderService extends CRUDService<Orders>{

    public OrderService(OrderRepository repository, Searcher searcher){
        this.repository=repository;
        this.searcher=searcher;
    }



}
