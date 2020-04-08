package com.loneliness.controller;


import com.loneliness.dto.BookDTO;
import com.loneliness.entity.domain.Book;
import com.loneliness.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController extends CommonController<Book,BookDTO>{

    public BookController(BookService bookService) {
        this.service = bookService;
    }

}
