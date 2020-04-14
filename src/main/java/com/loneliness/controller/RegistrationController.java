package com.loneliness.controller;

import com.loneliness.entity.Role;
import com.loneliness.entity.domain.User;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addUser(@RequestParam String username,@RequestParam String password) {
        if(userService.findByUsername(username).isPresent()) {
            return "registration";
        }
        else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setActive(true);


            user.setEmail("ekrasouski@gmail.com");

            user.setGoogleId("107510623782968017062");
            user.setLastVisit(Timestamp.valueOf(LocalDateTime.now()));
            user.setLocale("ru");
            user.setUsername("Ekrasouski Krasouski");
            user.setRoles(Collections.singleton(Role.USER));
            user.setUserPicture("https://lh5.googleusercontent.com/-OgmV1cz8oIA/AAAAAAAAAAI/AAAAAAAAAAA/AAKWJJOysRMYo2UYcP70a_vHB8CfBO694w/photo.jpg");
            userService.save(user);
            return "redirect:/login";
        }
    }
}
