package com.loneliness.entity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.loneliness.entity.OrderStatus;
import com.loneliness.entity.Role;
import com.loneliness.entity.Status;
import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bouncycastle.asn1.cms.TimeStampedData;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
    @NotNull(groups = {Exist.class})
    @Null(groups = {New.class})
    private Integer id;
    @NotEmpty(groups = {Exist.class , New.class })
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToMany(cascade = {
            CascadeType.REFRESH,
            CascadeType.MERGE
    },fetch = FetchType.EAGER)
    @JoinTable(name = "book_orders",
            joinColumns = @JoinColumn(name = "orders_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;
    @NotNull(groups = {Exist.class,New.class})
    @PastOrPresent(groups = {Exist.class,New.class})
    @JoinColumn(nullable = false)
    private Timestamp date;
    @PositiveOrZero(groups = {Exist.class,New.class} )
    private BigDecimal price;

    @NotNull(groups = {Exist.class, New.class})
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


}
