package com.loneliness.controller;

import com.loneliness.entity.Role;
import com.loneliness.entity.domain.User;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Controller

public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @Deprecated
    @PostMapping("/registration")
    public String addUser(@RequestParam String username,@RequestParam String password) {
        if(userService.findByUsername(username).isPresent()) {
            return "registration";
        }
        else {
            //
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setActive(false);


            user.setEmail("yojaca8107@johnderasia.com");

            user.setGoogleId("107511");
            user.setLastVisit(Timestamp.valueOf(LocalDateTime.now()));
            user.setLocale("ru");
            user.setUsername("1");
            user.setPassword("1");

            user.setRoles(Collections.singleton(Role.USER));
            user.setUserPicture("https://lh5.googleusercontent.com/-OgmV1cz8oIA/AAAAAAAAAAI/AAAAAAAAAAA/AAKWJJOysRMYo2UYcP70a_vHB8CfBO694w/photo.jpg");
            userService.addUser(user);
            return "redirect:/login";
        }
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }

        return "authPage";
    }
}
