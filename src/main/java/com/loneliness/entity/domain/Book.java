package com.loneliness.entity.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
public class Book implements Domain{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String author;
    private String genre;
    private BigDecimal price;
    private boolean availability;


}
