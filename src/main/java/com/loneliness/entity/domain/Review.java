package com.loneliness.entity.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = {"id","comment","mark","author","data"})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String comment;
    private int mark;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    private LocalDateTime data;
}
