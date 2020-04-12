package com.loneliness.controller;

import com.loneliness.dto.BookDTO;
import com.loneliness.dto.ReviewDTO;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Review;
import com.loneliness.entity.domain.User;
import com.loneliness.exception.NotFoundException;
import com.loneliness.repository.ReviewRepository;
import com.loneliness.service.ReviewService;
import com.loneliness.service.UserService;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
@Sql(value = {"/create-data-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

@Sql(value = {"/create-data-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TestReviewController {
    // TODO: 08.04.2020 разобраться с русскими символами га выходе из json
    private final  String reviewUri ="/review";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ReviewController reviewController;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;


    @Test
    public void AutowiredTest()throws Exception{
        assertThat(reviewController).isNotNull();
        assertThat(mockMvc).isNotNull();
        assertThat(reviewService).isNotNull();
    }


    @Test
    public void getReviewsTest()throws Exception{

         mockMvc.perform(MockMvcRequestBuilders.get(reviewUri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
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

                .andReturn();

    }


    @Test
    public void addValidTest()throws Exception{
        ReviewDTO dto = new ReviewDTO();
        dto.setAuthor(userService.findById(1).orElseThrow(NotFoundException::new));
        dto.setComment("comment");
        dto.setData(Timestamp.valueOf(LocalDateTime.now()));
        dto.setMark(5);
        String uri ="/books/"+5;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        dto.setBook(JsonParser.mapFromJson(result.getResponse().getContentAsString(), Book.class));

       result = mockMvc.perform(MockMvcRequestBuilders.post(reviewUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                // todo для проверки на содержимое необходимо обрезать время до минут или часов, мне пока лень , но проверку надо сделать
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());

    }


    @Test
    public void addInValidTest()throws Exception{
        ReviewDTO dto = new ReviewDTO();
        dto.setAuthor(new User());
        dto.setComment("");
        dto.setMark(-1);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(reviewUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());


    }

    @Test
    public void getReviewTest()throws Exception{
        int id =2;
        String uri =reviewUri+"/"+id;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        Review review = mapFromJson(result.getResponse().getContentAsString(), Review.class);

        assertEquals(reviewRepository.findById(id).get(),review);

    }

    @Test
    public void deleteTest()throws Exception{
        int id =1;
        String uri =reviewUri+"/"+id;
        mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;


        assertFalse(reviewRepository.findById(id).isPresent());

    }

    @Test
    public void updateTest()throws Exception{
        int id=2;
        ReviewDTO dto =new ReviewDTO();
        dto = dto.toDto(reviewService.findById(id).orElseThrow(NotFoundException::new));
        dto.setMark(1);
        dto.setAuthor(userService.findById(1).orElseThrow(NotFoundException::new));
        dto.setComment("new test comment");
        dto.setData(Timestamp.valueOf(LocalDateTime.now()));
        dto.setId(id);
        String uri =reviewUri+"/"+id;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(reviewService.findById(id).get(),dto.fromDTO());

    }


}
