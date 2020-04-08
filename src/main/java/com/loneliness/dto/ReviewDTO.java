package com.loneliness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.loneliness.entity.domain.Review;
import com.loneliness.entity.domain.User;
import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO implements DTO<Review>{
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
    private User author;
    @NotNull(groups = {Exist.class,New.class})
    @PastOrPresent(groups = {Exist.class,New.class})
    @JoinColumn(nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime data;

    @Override
    public Review fromDTO() {
        Review review = new Review();
        review.setAuthor(author);
        review.setComment(comment);
        review.setData(data);
        review.setId(id);
        review.setMark(mark);
        return review;
    }
}
