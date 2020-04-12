package com.loneliness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Review;
import com.loneliness.entity.domain.User;
import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
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
    @PositiveOrZero(groups = {Exist.class,New.class} )
    private Integer mark;
    @NotNull(groups = {Exist.class,New.class})
    private User author;
    @NotNull(groups = {Exist.class,New.class})
    @PastOrPresent(groups = {Exist.class,New.class})
    @JoinColumn(nullable = false)
 //   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp data;
    private Book book;

    @Override
    public Review fromDTO() {
        Review review = new Review();
        review.setAuthor(author);
        review.setComment(comment);
        review.setData(data);
        review.setId(id);
        review.setMark(mark);
        review.setSurveyedBook(book);
        return review;
    }
    public ReviewDTO toDto(Review review){
        ReviewDTO reviewDTO = new  ReviewDTO();
        reviewDTO.setAuthor(review.getAuthor());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setData(review.getData());
        reviewDTO.setId(review.getId());
        reviewDTO.setMark(review.getMark());
        reviewDTO.setBook(review.getSurveyedBook());
        return reviewDTO;
    }
}
