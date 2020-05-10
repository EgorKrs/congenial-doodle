package com.loneliness.controller;

import com.loneliness.dto.BookDTO;
import com.loneliness.dto.UserDTO;
import com.loneliness.entity.Role;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Review;
import com.loneliness.entity.domain.User;
import com.loneliness.repository.UserRepository;
import com.loneliness.service.UserService;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.loneliness.util.json_parser.JsonParser.mapFromJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "singlton")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-data-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-data-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TestUserController {
    private final  String url ="/user";

    @Autowired
    private UserService service;
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void AutowiredTest()throws Exception{
        assertThat(mockMvc).isNotNull();
        assertThat(service).isNotNull();
    }
    @Test
    public void getTest()throws Exception{

        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
       User[] users = JsonParser.mapFromJson(result.getResponse().getContentAsString(),User[].class);
       assertArrayEquals(service.findAll().toArray(new User[users.length]),users);
    }
     @Test
    public void addValidTest() throws Exception {
         User user = new User();
         user.setEmail("ekrasouski@gmail.com");
         user.setGoogleId("someval");
         user.setLastVisit(Timestamp.valueOf(LocalDateTime.now()));
         user.setLocale("ru");
         user.setUsername("Ekrasouski");
         Set<Role> roles = new HashSet<>();
         roles.add(Role.USER);
         roles.add(Role.ADMIN);
         user.setRoles(roles);
         user.setPassword("123321");
         user.setActive(false);
         UserDTO dto = UserDTO.toDto(user);
         dto.setCheckPassword("123321");
         MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                 .accept(MediaType.APPLICATION_JSON_VALUE)
                 .contentType(MediaType.APPLICATION_JSON_VALUE)
                 .content(JsonParser.mapToJson(dto)))
                 .andReturn();
         user = mapFromJson(result.getResponse().getContentAsString(), User.class);
         assertEquals(service.findById(user.getId()).get(),user);
     }
    @Test
    public void addInValidTest()throws Exception{
        User user = new User();
        user.setEmail("ekrasouski@gmail.com");
        user.setLastVisit(Timestamp.valueOf(LocalDateTime.now()));
        user.setLocale("");
        user.setUsername("");
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);
        user.setPassword("123321");
        user.setActive(false);
        UserDTO dto = UserDTO.toDto(user);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());


    }
    @Test
    public void searchTest()throws Exception{

        final List<SearchCriteria> params = new ArrayList<>();
        String id="1";
        params.add( SearchCriteria.builder().operation("=").key("id").value(id).build());
        String uri ="/search/User";
       mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(params)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }
    @Test
    public void getByIdTest()throws Exception{
        int id =1;
        String uri =url+"/"+id;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        User user = mapFromJson(result.getResponse().getContentAsString(), User.class);

        assertEquals(service.findById(id).get(),user);
    }
    @Test
    public void deleteTest()throws Exception{
        int id =11;
        String uri = url + "/" +id;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());


        assertFalse(service.findById(id).isPresent());

    }
}
