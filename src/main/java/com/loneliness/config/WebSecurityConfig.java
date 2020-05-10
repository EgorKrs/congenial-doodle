package com.loneliness.config;



import com.loneliness.entity.Role;
import com.loneliness.entity.domain.User;
import com.loneliness.repository.UserRepository;
import com.loneliness.service.UserService;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;

@Configuration
@EnableWebSecurity
//@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(UserService userService,PasswordEncoder passwordEncoder){
        this.userService=userService;
        this.passwordEncoder=passwordEncoder;
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/js/**", "/error**","/home","/registration**","/activate/*").permitAll()
                .antMatchers("/books","/search","/search/*","/review","/login/*").permitAll()
                .antMatchers("/books/**","/review/*").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().rememberMe()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }

    // TODO: 16.04.2020 из за конфликта авторизаций EnableOAuth2Sso и кастомной временно oath идет далеко
//    @Bean
//    public PrincipalExtractor principalExtractor(UserRepository repository) {
//        return map -> {
//            String id = (String) map.get("sub");
//
//            User user = repository.findUserByGoogleId(id).orElseGet(() -> {
//                User newUser = new User();
//
//                newUser.setGoogleId(id);
//                newUser.setRoles(Collections.singleton(Role.USER));
//                newUser.setUsername((String) map.get("name"));
//                newUser.setEmail((String) map.get("email"));
//                newUser.setLocale((String) map.get("locale"));
//                newUser.setUserPicture((String) map.get("picture"));
//
//                return newUser;
//            });
//
//            user.setLastVisit(Timestamp.valueOf(LocalDateTime.now()));
//
//            return repository.save(user);
//        };
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}
