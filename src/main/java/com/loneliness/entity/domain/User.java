package com.loneliness.entity.domain;

import com.loneliness.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = {"id","name","role","email","locale","lastVisit"})
public class User implements Domain{
    @Id
    private int id;
    @Column(name = "googleId", unique = true)
    private String googleId;
    private String name;
    private Role role;
    private String gender;
    private String userPicture;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    private String locale;
    private LocalDateTime lastVisit;

}
