package com.loneliness.controller;


import com.loneliness.entity.domain.Book;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.BookService;
import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("{id}")
    public Book getOneById(@PathVariable Integer id) {
        return find(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces =  MediaType.APPLICATION_JSON_VALUE)
    public Book create(@Validated(New.class) @RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping(value = "{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces =  MediaType.APPLICATION_JSON_VALUE)
    public Book update(@Validated(Exist.class) @RequestBody Book book, @PathVariable Integer id) {
        find(id);
        return bookService.save(book);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id){
        bookService.delete(id);
    }

    private Book find(@PathVariable Integer id) {
        return bookService.findById(id).orElseThrow(NotFoundException::new);
    }


}
