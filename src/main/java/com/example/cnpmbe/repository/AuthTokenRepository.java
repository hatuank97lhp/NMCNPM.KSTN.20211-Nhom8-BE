package com.example.cnpmbe.repository;


import com.example.cnpmbe.model.entity.auth.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    Optional<AuthToken> findAuthTokenByUserId(Long userId);

    Optional<AuthToken> findAuthTokenByAccessToken(String accessToken);
}

