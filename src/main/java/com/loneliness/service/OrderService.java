package com.loneliness.service;

import com.loneliness.entity.Status;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Orders;
import com.loneliness.repository.BookRepository;
import com.loneliness.repository.OrderRepository;
import com.loneliness.util.search.SearchCriteria;
import com.loneliness.util.search.Searcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class OrderService extends CRUDService<Orders> {


    public OrderService(OrderRepository repository, SearchService searcher) {
        this.repository = repository;
        this.searchService = searcher;

    }


    public Orders create(Orders note) {
        // TODO: 07.05.2020 добавить возможность учета различных акций и предложений
        List<Book> books = new LinkedList<>(note.getBooks());
        BigDecimal price = new BigDecimal("0");

        for (Book book :
                books) {
            price = price.add(searchService.search(
                    SearchCriteria.builder()
                            .operation("=")
                            .key("id")
                            .value(book.getId())
                            .build()
                    , Book.class
            ).getPrice());
        }
        note.setPrice(price);
        note.setStatus(Status.CHECKOUT_IN_PROGRESS);
        return repository.save(note);
    }


}
