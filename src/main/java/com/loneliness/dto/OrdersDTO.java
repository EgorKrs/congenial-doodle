package com.loneliness.dto;

import com.loneliness.entity.Status;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Orders;
import com.loneliness.entity.domain.User;
import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO implements DTO<Orders>{
    @Id
    @Null(groups = {New.class})
    @NotNull(groups = {Exist.class})
    private Integer id;
    @NotNull(groups = {Exist.class , New.class })
    private Status status;
    @NotEmpty(groups = {Exist.class , New.class })
    private List<Book> books;
    @NotNull(groups = {Exist.class,New.class})
    @PastOrPresent(groups = {Exist.class,New.class})
    @JoinColumn(nullable = false)
    private Timestamp date;
    @PositiveOrZero(groups = {Exist.class,New.class} )
    private BigDecimal price;
    @NotNull(groups = {Exist.class, New.class})
    private User user;

    public Orders fromDTO(){
        Orders orders = new Orders();
        orders.setId(id);
        orders.setStatus(status);
        orders.setBooks(books);
        orders.setDate(date);
        orders.setPrice(price);
        orders.setUser(user);
        return orders;
    }
    public static OrdersDTO toDTO(Orders orders){
        OrdersDTO dto = new OrdersDTO();
        dto.setId(orders.getId());
        dto.setStatus(orders.getStatus());
        dto.setBooks(orders.getBooks());
        dto.setDate(orders.getDate());
        dto.setPrice(orders.getPrice());
        dto.setUser(orders.getUser());
        return dto;
    }
}
