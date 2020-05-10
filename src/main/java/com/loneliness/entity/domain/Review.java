package com.loneliness.entity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Table( uniqueConstraints = {@UniqueConstraint(columnNames={"book_id", "author_id"})})
@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = {"id","comment","mark","author","data"})
public class Review implements Domain{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Null(groups = New.class)
    @NotNull(groups = Exist.class)
    private Integer id;
    @NotNull(groups = {Exist.class,New.class})
    private String comment;
    @NotNull(groups = {Exist.class,New.class})
    @PositiveOrZero(groups = {Exist.class,New.class})
    private Integer mark;
    @NotNull(groups = {Exist.class,New.class})
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;


    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "book_id" )
    @JsonIgnore
    private Book surveyedBook;



    @NotNull(groups = {Exist.class,New.class})
    @PastOrPresent(groups = {Exist.class,New.class})
    @JoinColumn(nullable = false)
    //@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private Timestamp data;


}
