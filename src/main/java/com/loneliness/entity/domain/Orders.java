package com.loneliness.entity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.loneliness.entity.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bouncycastle.asn1.cms.TimeStampedData;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
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
    private Integer id;
    private String status;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Book> books;
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp date;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


}
