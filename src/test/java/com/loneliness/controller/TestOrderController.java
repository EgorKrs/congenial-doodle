package com.loneliness.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loneliness.dto.BookDTO;
import com.loneliness.dto.OrdersDTO;
import com.loneliness.entity.Status;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Orders;
import com.loneliness.service.BookService;
import com.loneliness.service.OrderService;
import com.loneliness.service.UserService;
import com.loneliness.util.json_parser.JsonParser;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com.loneliness.util.json_parser.JsonParser.mapFromJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "singlton")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-data-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-data-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TestOrderController {
    private final   String uri ="/orders";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderController controller;

    @Autowired
    private OrderService service;

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @Test
    public void AutowiredTest()throws Exception{
        assertThat(controller).isNotNull();
        assertThat(mockMvc).isNotNull();
        assertThat(service).isNotNull();
    }

    @Test
    public void getOrdersTest()throws Exception{

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

    }
    @Test
    public void addValidTest()throws Exception{
        OrdersDTO dto =new OrdersDTO();
       // dto.setId(0);
        dto.setBooks(Arrays.asList(bookService.findById(1).get(),bookService.findById(2).get()));
        dto.setStatus(Status.CHECKOUT_IN_PROGRESS);
        dto.setDate(Timestamp.valueOf(LocalDateTime.now()));
        dto.setUser(userService.findByUsername("singlton").get());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(dto)))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Orders orders = mapFromJson(result.getResponse().getContentAsString(), Orders.class);
        assertEquals(service.findById(orders.getId()).get(),orders);

    }
    @Test
    public void addInValidTest()throws Exception{
        OrdersDTO dto =new OrdersDTO();
        dto.setId(30);
        dto.setBooks(Arrays.asList(bookService.findById(1).get(),bookService.findById(2).get()));
        dto.setDate(Timestamp.valueOf(LocalDateTime.now()));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());


    }
    @Test
    public void deleteTest()throws Exception{
        int id =9;
        String url = uri + "/" +id;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(url)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200, result.getResponse().getStatus());


        assertFalse(service.findById(id).isPresent());

    }
    @Test
    public void updateTest()throws Exception{

        Orders order =service.findById(10).get();
        order.setStatus(Status.ACCEPTED);
        order.getBooks().remove(1);
        order.getBooks().add(bookService.findById(5).get());
        OrdersDTO dto = OrdersDTO.toDTO(order);
        String url =uri+"/"+dto.getId();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(url)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(service.findById(dto.getId()).get(),dto.fromDTO());

    }


}
