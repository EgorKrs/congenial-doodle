package com.loneliness.entity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

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
    @PositiveOrZero(groups = {Exist.class,New.class} )
    private BigDecimal price;
    @NotBlank(groups = {Exist.class,New.class} )
    private Boolean availability;
    @PositiveOrZero(groups = {Exist.class,New.class} )
    private Integer quantity;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "surveyedBook")
    private List<Review> reviews;
    @ManyToMany(mappedBy = "books")
    @JsonIgnore
    private List<Orders> orders;

}
