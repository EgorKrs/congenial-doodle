package com.loneliness.util;

import com.loneliness.entity.domain.Book;
import com.loneliness.util.search.Searcher;
import com.loneliness.util.search.SearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilTest {
    @Autowired
    private Searcher searcher;

    @Test
    public void searchListTest(){
        final List<SearchCriteria> params = new ArrayList<>();
        params.add( SearchCriteria.builder().operation("=").key("name").value("book1").build());
        List <Book> books =searcher.search(params,Book.class);
        books.forEach(System.out::println);
    }
    @Test
    public void searchMasTest(){
        SearchCriteria[] params = new SearchCriteria[1];
        params[0] = SearchCriteria.builder().operation("=").key("name").value("book1").build();
        List <Book> books =searcher.search(params,Book.class);
        books.forEach(System.out::println);
    }

}
