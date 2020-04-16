package com.loneliness.controller;

import com.loneliness.dto.CaptchaResponseDto;
import com.loneliness.dto.UserDTO;
import com.loneliness.entity.Role;
import com.loneliness.entity.domain.User;
import com.loneliness.exception.DataIsAlreadyExistException;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.UserService;
import com.loneliness.transfer.New;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Controller

public class RegistrationController {
    private final UserService userService;
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Value("${recaptcha.secret}")
    private String secret;
    private final RestTemplate restTemplate;
    public RegistrationController(UserService userService,    RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate=restTemplate;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @Deprecated
    @PostMapping("/registration")
    public String addUser(@RequestParam("g-recaptcha-response") String captchaResponse,
                          @Validated(New.class) UserDTO dto,
                          BindingResult bindingResult,
                          Model model
    ) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        boolean isConfirmEmpty = StringUtils.isEmpty(StringUtils.isEmpty(dto.getCheckPassword()));
        assert response != null;
        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha");
        }
        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }
        if (dto.getPassword() != null && !dto.getPassword().equals(dto.getCheckPassword())) {
            model.addAttribute("passwordError", "Passwords are different!");
        }

        if (isConfirmEmpty || bindingResult.hasErrors() || !response.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "registration";
        }
        try {
            userService.addUser(dto.fromDTO());
        }catch (DataIsAlreadyExistException e){
            model.addAttribute("error", e.getMessage());
            return "registration";
        }
        return "redirect:/login";

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
