package com.loneliness.entity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.loneliness.entity.Role;
import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import com.loneliness.util.json_parser.CustomAuthorityDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;


@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = {"id","username","roles","email","locale","lastVisit"})
public class User implements Domain, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Null(groups = New.class)
    @NotNull(groups = Exist.class)
    @Positive(groups = Exist.class)
    private Integer id;
    @Length(max = 255,groups = {New.class,Exist.class} )
    @Column(name = "google_Id", unique = true)
    private String googleId;
    @Length(max = 255,groups = {New.class,Exist.class} )
    @NotBlank(groups = {New.class,Exist.class})
    @Column(name = "username", nullable = false,unique = true)
    private String username;
    @Length(max = 255,groups = {New.class,Exist.class} )
    @NotBlank(groups = {New.class,Exist.class})
    @Column( nullable = false)
    private String password;
    private boolean active;
    //@JsonDeserialize(using = CustomAuthorityDeserializer.class)
    @NotEmpty(groups = {New.class,Exist.class})
    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    @Length(groups = {New.class,Exist.class} )
    private String userPicture;
    @NotBlank(groups = {New.class,Exist.class})
    @Email(groups = {New.class,Exist.class})
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    private String activationCode;
    @Length(max = 255,groups = {New.class,Exist.class} )
    private String locale;
    @PastOrPresent(groups = {New.class,Exist.class})
    private Timestamp lastVisit;
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return active;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return active;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return active;
    }
}
