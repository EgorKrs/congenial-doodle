package com.loneliness;

import com.loneliness.dto.ReviewDTO;
import com.loneliness.entity.domain.Review;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.ReviewService;
import com.loneliness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@SpringBootApplication
public class Main  extends SpringBootServletInitializer {



    public static void main(String[] args) {


        SpringApplication.run(Main.class,args);



    }



}
