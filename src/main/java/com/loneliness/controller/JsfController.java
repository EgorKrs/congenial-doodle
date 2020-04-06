package com.loneliness.controller;

import com.loneliness.entity.domain.Book;
import com.loneliness.service.BookService;
import com.loneliness.util.search.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Scope(value = "session")
@Component(value = "bookController")

public class JsfController {
    private int id;
    private String name;
    private String author;
    private String genre;
    private BigDecimal price;
    private String ex;
    private boolean availability;

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isAvailability() {
        return availability;
    }
    private String key;
    private String operation;
    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Autowired
    BookService bookService;

    @RequestMapping("/saveBook")
    public void saveBook(){
        Book book = new Book();
        book.setId(id);
        book.setPrice(price);
        book.setName(name);
        book.setGenre(genre);
        book.setAvailability(availability);
        book.setAuthor(author);
       bookService.save(book);
    }
    @RequestMapping("/delBook")
    public void delBook(){
        if(id!=0) {
            try {
                bookService.delete(id);
            }
            catch (org.springframework.dao.EmptyResultDataAccessException ex){
                System.out.println(ex.getLocalizedMessage());
                setEx(ex.getLocalizedMessage());
            }
        }
    }
    public void delBook(Book book){
        System.out.println(book);
    }

    public List<Book> find(){
        if(operation!=null && key!=null && value !=null) {
            final List<SearchCriteria> params = new ArrayList<>();
            params.add(SearchCriteria.builder().operation(operation).key(key).value(value).build());
            return bookService.search(params, Book.class);
        }
        else return bookService.findAll();
    }
}
