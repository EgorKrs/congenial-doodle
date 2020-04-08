package com.loneliness.dto;

import com.loneliness.entity.domain.Book;
import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO implements DTO<Book>{

    @Null(groups = {New.class})
    @NotNull(groups = {Exist.class})
    private Integer id;
    @NotBlank(groups = {Exist.class,New.class})
    private String name;
    @NotBlank(groups = {Exist.class,New.class})
    private String author;
    @NotBlank(groups = {Exist.class,New.class})
    private String genre;
    @NotNull(groups = {Exist.class,New.class})
    @PositiveOrZero(groups = {Exist.class,New.class})
    private BigDecimal price;
    @NotNull(groups = {Exist.class,New.class})
    private Boolean availability;

    public void setPrice(String price){
        this.price = new BigDecimal(price);
    }

    public Book fromDTO(){
        Book book = new Book();
        book.setAuthor(author);
        book.setGenre(genre);
        book.setName(name);
        book.setPrice(price);
        book.setAvailability(availability);
        book.setId(id);
        return book;
    }

}
