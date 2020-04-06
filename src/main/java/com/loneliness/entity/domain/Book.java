package com.loneliness.entity.domain;

import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
public class Book implements Domain{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(groups = {Exist.class})
    @Null(groups = {New.class})
    private int id;
    @NotNull(groups = {Exist.class})
    private String name;
    @NotNull(groups = {Exist.class})
    private String author;
    @NotNull(groups = {Exist.class})
    private String genre;
    @NotNull(groups = {Exist.class})
    @PositiveOrZero
    private BigDecimal price;
    @NotNull(groups = {Exist.class})
    private Boolean availability;


}
