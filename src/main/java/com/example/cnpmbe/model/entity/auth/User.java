package com.example.cnpmbe.model.entity.auth;


import com.example.cnpmbe.model.request.auth.UserRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "user_acc_cnpm", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_acc_id_seq")
    @SequenceGenerator(
            name = "user_acc_id_seq",
            sequenceName = "user_acc_id_seq",
            allocationSize = 1
    )
    private Long id;

    @NotNull(message = "Username must not be null")
    @Size(max = 255, message = "Username's length must be less than 255")
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull(message = "Password must not be null")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name")
    @Size(max = 255, message = "Name's length must be less than 255")
    private String name;

    private boolean isDeleted = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    public User(UserRequest userRequest) {
        this.username = userRequest.getUsername();
        this.name = userRequest.getName();
        this.password = userRequest.getPassword();
    }
}
