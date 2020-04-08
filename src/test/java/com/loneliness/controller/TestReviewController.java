package com.loneliness.controller;

import com.loneliness.dto.BookDTO;
import com.loneliness.entity.domain.Book;
import com.loneliness.repository.ReviewRepository;
import com.loneliness.service.ReviewService;
import com.loneliness.util.json_parser.JsonParser;
import com.loneliness.util.search.SearchCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WithUserDetails(value = "Ekrasouski Krasouski")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-book-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-book-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TestReviewController {
    // TODO: 08.04.2020 разобраться с русскими символами га выходе из json
    private final  String ReviewUri ="/review";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ReviewController reviewController;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;


    @Test
    public void AutowiredTest()throws Exception{
        assertThat(reviewController).isNotNull();
        assertThat(mockMvc).isNotNull();
        assertThat(reviewService).isNotNull();
    }


    @Test
    public void getReviewsTest()throws Exception{

         mockMvc.perform(MockMvcRequestBuilders.get(ReviewUri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().string("[{\"id\":1,\"comment\":\"Ð»Ñ\u0083Ñ\u0087Ñ\u0088Ð°Ñ\u008F\",\"mark\":10,\"author\":{\"id\":1,\"googleId\":\"107510623782968017062\",\"name\":\"Ekrasouski Krasouski\",\"role\":\"USER\",\"gender\":null,\"userPicture\":\"https://lh5.googleusercontent.com/-OgmV1cz8oIA/AAAAAAAAAAI/AAAAAAAAAAA/AAKWJJOysRMYo2UYcP70a_vHB8CfBO694w/photo.jpg\",\"email\":\"ekrasouski@gmail.com\",\"locale\":\"ru\",\"lastVisit\":\"2020-04-06 16:45:53\"},\"data\":null},{\"id\":2,\"comment\":\"Ñ\u0085Ñ\u0083Ð´Ñ\u0088Ð°Ñ\u008F\",\"mark\":1,\"author\":{\"id\":1,\"googleId\":\"107510623782968017062\",\"name\":\"Ekrasouski Krasouski\",\"role\":\"USER\",\"gender\":null,\"userPicture\":\"https://lh5.googleusercontent.com/-OgmV1cz8oIA/AAAAAAAAAAI/AAAAAAAAAAA/AAKWJJOysRMYo2UYcP70a_vHB8CfBO694w/photo.jpg\",\"email\":\"ekrasouski@gmail.com\",\"locale\":\"ru\",\"lastVisit\":\"2020-04-06 16:45:53\"},\"data\":null}]"))
                .andReturn();

    }

    @Test
    public void searchTest()throws Exception{

        final List<SearchCriteria> params = new ArrayList<>();
        String reviewText="лучшая";
        params.add( SearchCriteria.builder().operation("=").key("comment").value(reviewText).build());
        String uri ="/search/Review";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(params)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().string("[{\"id\":1,\"comment\":\"Ð»Ñ\u0083Ñ\u0087Ñ\u0088Ð°Ñ\u008F\",\"mark\":10,\"author\":{\"id\":1,\"googleId\":\"107510623782968017062\",\"name\":\"Ekrasouski Krasouski\",\"role\":\"USER\",\"gender\":null,\"userPicture\":\"https://lh5.googleusercontent.com/-OgmV1cz8oIA/AAAAAAAAAAI/AAAAAAAAAAA/AAKWJJOysRMYo2UYcP70a_vHB8CfBO694w/photo.jpg\",\"email\":\"ekrasouski@gmail.com\",\"locale\":\"ru\",\"lastVisit\":{\"nano\":252691000,\"year\":2020,\"monthValue\":4,\"dayOfMonth\":6,\"hour\":16,\"minute\":45,\"second\":53,\"dayOfWeek\":\"MONDAY\",\"dayOfYear\":97,\"month\":\"APRIL\",\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}}},\"data\":null}]"))
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
        assertEquals(reviewService.findById(book.getId()).get(),book);

    }

    // TODO: 08.04.2020 переделать остальное на review
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
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200, result.getResponse().getStatus());

        Book book = mapFromJson(result.getResponse().getContentAsString(), Book.class);

        assertEquals(reviewRepository.findById(id).get(),book);

    }

    @Test
    public void deleteTest()throws Exception{
        int id =4;
        String uri ="/books/"+id;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200, result.getResponse().getStatus());


        assertFalse(reviewRepository.findById(id).isPresent());

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
        assertEquals(reviewService.findById(id).get(),bookDTO.fromDTO());

    }


}
