package com.example.cnpmbe.model.entity.auth;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "auth_token_cnpm", uniqueConstraints = {
        @UniqueConstraint(name = "access_token_uq", columnNames = {"user_id", "access_token"}),
        @UniqueConstraint(name = "refresh_token_uq", columnNames = {"user_id", "refresh_token"})})
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_token_id_seq")
    @SequenceGenerator(
            name = "auth_token_id_seq",
            sequenceName = "auth_token_id_seq",
            allocationSize = 1
    )
    private Long id;

    @NotNull(message = "refresh_token must not be null")
    @Size(max = 2048, message = "Refresh_Token's length should be less than 255 characters")
    @Column(name = "refresh_token", length = 2048)
    private String refreshToken;

    @NotNull(message = "access_token must not be null")
    @Size(max = 2048, message = "access_token's length should be less than 255 characters")
    @Column(name = "access_token", length = 2048)
    private String accessToken;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant expiredIn;

    @NotNull(message = "User_Id must not be null")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}