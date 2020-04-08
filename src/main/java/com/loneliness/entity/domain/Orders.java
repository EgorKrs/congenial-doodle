package com.loneliness.entity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.loneliness.entity.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
public class Orders implements Domain{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String status;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Book> books;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;



}
