package com.loneliness.entity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.loneliness.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = {"id","name","role","email","locale","lastVisit"})
public class User implements Domain{
    @Id
    private Integer id;
    @Column(name = "google_Id", unique = true)
    private String googleId;
    @Column(name = "name", nullable = false)
    private String name;
    private Role role;
    private String gender;
    private String userPicture;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    private String locale;
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp lastVisit;

}
