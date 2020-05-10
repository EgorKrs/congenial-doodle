package com.loneliness.controller;

import com.loneliness.dto.BookDTO;
import com.loneliness.entity.domain.Book;
import com.loneliness.repository.BookRepository;
import com.loneliness.service.BookService;
import com.loneliness.util.json_parser.JsonParser;
import com.loneliness.util.search.SearchCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;


import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static com.loneliness.util.json_parser.JsonParser.mapFromJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "singlton")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-data-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-data-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TestBookController {

     @Autowired
     private MockMvc mockMvc;

     @Autowired
     BookController bookcontroller;

     @Autowired
    BookRepository bookRepository;

    @Autowired
    BookService bookService;


    @Test
     public void AutowiredTest()throws Exception{
          assertThat(bookcontroller).isNotNull();
          assertThat(mockMvc).isNotNull();
        assertThat(bookService).isNotNull();
     }


     @Test
     public void getBooksTest()throws Exception{
         String uri ="/books";
         MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                  .accept(MediaType.APPLICATION_JSON_VALUE))
                 .andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                 .andReturn();

     }

    @Test
    public void searchTest()throws Exception{

        final List<SearchCriteria> params = new ArrayList<>();
        String bookName="book1";
        params.add( SearchCriteria.builder().operation("=").key("name").value(bookName).build());
        String uri ="/search/Book";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(params)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

    }

    @Test
    public void addValidTest()throws Exception{
        BookDTO bookDTO =new BookDTO();
        bookDTO.setAuthor("nickName");
        bookDTO.setAvailability(true);
        bookDTO.setGenre("genre");
        bookDTO.setName("name");
        bookDTO.setPrice("20");

        String uri ="/books";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(bookDTO)))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Book book = mapFromJson(result.getResponse().getContentAsString(), Book.class);
        assertEquals(bookService.findById(book.getId()).get(),book);

    }

    @Test
    public void addInValidTest()throws Exception{
        BookDTO bookDTO =new BookDTO();
        bookDTO.setAuthor("");
        bookDTO.setAvailability(true);
        bookDTO.setGenre("");
        bookDTO.setName("");
        bookDTO.setPrice("-20");

        String uri ="/books";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(bookDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());


    }

    @Test
    public void getBookTest()throws Exception{
        int id =2;
        String uri ="/books/"+id;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

    }

    @Test
    public void deleteTest()throws Exception{
        int id =4;
        String uri ="/books/"+id;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200, result.getResponse().getStatus());


        assertFalse(bookRepository.findById(id).isPresent());

    }

    @Test
    public void updateTest()throws Exception{
        int id=3;
        BookDTO bookDTO =new BookDTO();
        bookDTO.setAuthor("nickName");
        bookDTO.setAvailability(true);
        bookDTO.setGenre("genre");
        bookDTO.setName("name");
        bookDTO.setPrice("20");
        bookDTO.setId(3);

        String uri ="/books/"+id;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(bookDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(bookService.findById(id).get(),bookDTO.fromDTO());

    }





}
