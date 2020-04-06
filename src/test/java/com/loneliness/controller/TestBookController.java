package com.loneliness.controller;

import com.loneliness.entity.domain.Book;
import com.loneliness.repository.BookRepository;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WithUserDetails(value = "Ekrasouski Krasouski")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-book-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-book-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TestBookController {

     @Autowired
     private MockMvc mockMvc;

     @Autowired
     BookController bookcontroller;

     @Autowired
    BookRepository bookRepository;

    protected String mapToJson(Object obj) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

     @Test
     public void AutowiredTest()throws Exception{
          assertThat(bookcontroller).isNotNull();
          assertThat(mockMvc).isNotNull();
     }
//     @Test
//     public void AccessTest()throws Exception{
//          this.mockMvc.perform(get("/books"))
//                  .andDo(print())
//                  .andExpect(status().isOk());
//          this.mockMvc.perform(get("/books/3"))
//                  .andDo(print())
//                  .andExpect(status().is3xxRedirection());
//          this.mockMvc.perform(delete("/books/3"))
//                  .andDo(print())
//                  .andExpect(status().is3xxRedirection());
//          this.mockMvc.perform(put("/books/3"))
//                  .andDo(print())
//                  .andExpect(status().is3xxRedirection());
//          this.mockMvc.perform(post("/books/3"))
//                  .andDo(print())
//                  .andExpect(status().is3xxRedirection());
//     }
//     @Test
//     public void BooksListTest()throws Exception{
//          this.mockMvc.perform(get("/books"))
//                  .andDo(print())
//                  .andExpect(xpath("/html/body/pre").string("[{\"id\":1,\"name\":\"book1\",\"author\":\"author1\",\"genre\":\"horror\",\"price\":20.00,\"availability\":true},{\"id\":2,\"name\":\"book\",\"author\":\"auhor\",\"genre\":\"a\",\"price\":2.00,\"availability\":false},{\"id\":3,\"name\":\"asdas\",\"author\":\"author1\",\"genre\":\"action\",\"price\":20.00,\"availability\":false},{\"id\":4,\"name\":\"book2\",\"author\":\"author2\",\"genre\":\"action\",\"price\":50.30,\"availability\":false},{\"id\":5,\"name\":\"book2\",\"author\":\"author2\",\"genre\":\"action\",\"price\":50.30,\"availability\":false}]"));
//     }

     @Test
     public void getBooksTest()throws Exception{
         String uri ="/books";
         MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                  .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

          assertEquals(200, result.getResponse().getStatus());

         Book[] books =mapFromJson(result.getResponse().getContentAsString(), Book[].class);
          assertArrayEquals(bookRepository.findAll().toArray( new Book[books.length]),books);

     }

    @Test
    public void getBookTest()throws Exception{
        int id =2;
        String uri ="/books/"+id;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200, result.getResponse().getStatus());

        Book book = mapFromJson(result.getResponse().getContentAsString(), Book.class);

        assertEquals(bookRepository.findById(id).get(),book);

    }




}
