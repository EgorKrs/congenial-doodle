package com.loneliness.dto;

import com.loneliness.entity.Role;
import com.loneliness.entity.domain.User;
import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements DTO<User> {
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
    @Column(name = "username", nullable = false)
    private String username;
    @Length(max = 255,groups = {New.class,Exist.class} )
    @NotBlank(groups = {New.class,Exist.class})
    private String password;
    @Length(max = 255,groups = {New.class,Exist.class} )
    @NotBlank(groups = {New.class,Exist.class})
    private String checkPassword;
    private boolean active;
    @NotEmpty(groups = {Exist.class})
    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    @Length(max = 255,groups = {New.class,Exist.class} )
    private String userPicture;
    @NotBlank(groups = {New.class,Exist.class})
    @Email(groups = {New.class,Exist.class})
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Length(max = 255,groups = {New.class,Exist.class} )
    private String locale;
    @PastOrPresent(groups = {New.class,Exist.class})
    private Timestamp lastVisit;

    @Override
    public User fromDTO() {
        User user = new User();
        user.setId(id);
        user.setGoogleId(googleId);
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(active);
        user.setRoles(roles);
        user.setUserPicture(userPicture);
        user.setEmail(email);
        user.setLastVisit(lastVisit);
        return user;
    }
}
