package com.loneliness.controller;

import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Orders;
import com.loneliness.entity.domain.Review;
import com.loneliness.entity.domain.User;
import com.loneliness.exception.BadArgumentException;
import com.loneliness.service.SearchService;
import com.loneliness.util.json_parser.JsonParser;
import com.loneliness.util.search.SearchCriteria;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
    @PostMapping(value = "{tClass}",consumes = MediaType.APPLICATION_JSON_VALUE,produces =  MediaType.APPLICATION_JSON_VALUE)
    public String search(@Validated @RequestBody SearchCriteria[] params, @PathVariable String tClass) throws IOException {
        switch (tClass) {
            case "Book":
                return JsonParser.mapToJson(searchService.search(params, Book.class));
            case "Orders":
                return JsonParser.mapToJson(searchService.search(params, Orders.class));
            case "Review":
                return JsonParser.mapToJson(searchService.search(params, Review.class));
            case "User":
                return JsonParser.mapToJson(searchService.search(params, User.class));
            default: throw new BadArgumentException();
        }
    }
}



