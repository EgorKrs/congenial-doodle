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
public class Review implements Domain{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String comment;
    private Integer mark;
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;
    private LocalDateTime data;
}
