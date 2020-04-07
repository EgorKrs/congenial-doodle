package com.loneliness.controller;


import com.loneliness.dto.BookDTO;
import com.loneliness.entity.domain.Book;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.BookService;
import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import com.loneliness.util.json_parser.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

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
    public String create(@Validated(New.class) @RequestBody BookDTO bookDTO) throws IOException {
        return JsonParser.mapToJson(bookService.save(bookDTO.fromDTO()));
    }

    @PutMapping(value = "{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces =  MediaType.APPLICATION_JSON_VALUE)
    public Book update(@Validated(Exist.class) @RequestParam BookDTO bookDTO, @PathVariable Integer id)  {
        find(id);
        return bookService.save(bookDTO.fromDTO());
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id){
        bookService.delete(id);
    }

    private Book find(@PathVariable Integer id) {
        return bookService.findById(id).orElseThrow(NotFoundException::new);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        errors.forEach((s, s2) -> System.out.println(s2));
        return errors;
    }

}
