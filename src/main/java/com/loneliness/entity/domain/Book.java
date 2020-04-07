package com.loneliness.entity.domain;

import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(groups = {Exist.class})
    @Null(groups = {New.class})
    private Integer id;
    @NotBlank(groups = {Exist.class,New.class})
    private String name;
    @NotBlank(groups = {Exist.class,New.class} )
    private String author;
    @NotBlank(groups = {Exist.class,New.class} )
    private String genre;
    @NotBlank(groups = {Exist.class,New.class} )
    @PositiveOrZero
    private BigDecimal price;
    @NotBlank(groups = {Exist.class,New.class} )
    private Boolean availability;


}
