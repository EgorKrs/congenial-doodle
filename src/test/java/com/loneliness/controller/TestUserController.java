package com.loneliness.controller;

import com.loneliness.entity.Role;
import com.loneliness.entity.domain.User;
import com.loneliness.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserController {
    @Autowired
    UserRepository userRepository;

     @Test
    public void addTest(){
         User user = new User();
         user.setId(1);
         user.setEmail("ekrasouski@gmail.com");
         user.setGoogleId("107510623782968017062");
         user.setLastVisit(Timestamp.valueOf(LocalDateTime.now()));
         user.setLocale("ru");
         user.setUsername("Ekrasouski Krasouski");
         user.setRoles(Collections.singleton(Role.ADMIN));
         user.setUserPicture("https://lh5.googleusercontent.com/-OgmV1cz8oIA/AAAAAAAAAAI/AAAAAAAAAAA/AAKWJJOysRMYo2UYcP70a_vHB8CfBO694w/photo.jpg");
         userRepository.save(user);

         System.out.println(userRepository.findUserByGoogleId("107510623782968017062"));
     }
}
