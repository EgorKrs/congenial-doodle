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
public class User {
    @Id
    private String id;
    private String name;
    private Role role;
    private String userPicture;
    private String email;
    private String locale;
    private LocalDateTime lastVisit;

}
