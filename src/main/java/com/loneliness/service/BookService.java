package com.loneliness.service;

import com.loneliness.entity.domain.Book;
import com.loneliness.repository.BookRepository;
import com.loneliness.util.search.SearchCriteria;
import com.loneliness.util.search.Searcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class BookService extends CRUDService<Book>{
    public BookService(BookRepository bookRepository, Searcher searcher){
        repository=bookRepository;
        this.searcher = searcher;
    }



}
