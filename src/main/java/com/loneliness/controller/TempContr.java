package com.loneliness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempContr {
    @GetMapping("/authPage")
    public String registration() {
        return "authPage";
    }
}
