package com.example.cnpmbe.jwt;

import com.example.cnpmbe.common.Constants;
import com.example.cnpmbe.config.EnvConfig;
import com.example.cnpmbe.model.entity.auth.AuthToken;
import com.example.cnpmbe.model.entity.auth.User;
import com.example.cnpmbe.repository.AuthTokenRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class JwtTokenProvider {

    @Autowired
    public AuthTokenRepository authTokenRepository;

    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EnvConfig.ACCESS_TOKEN_TIME * 1000L);
        User user = userDetails.getUser();

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getUser().getId()))
                .claim(Constants.USERNAME_KEY, user.getUsername())
                .claim(Constants.EMAIL_KEY, user.getName())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, EnvConfig.JWT_SECRET_KEY)
                .compact();
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EnvConfig.ACCESS_TOKEN_TIME);

        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .claim(Constants.USERNAME_KEY, user.getUsername())
                .claim(Constants.EMAIL_KEY, user.getName())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, EnvConfig.JWT_SECRET_KEY)
                .compact();
    }

    public User getUserFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(EnvConfig.JWT_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        User user = new User();
        user.setId(Long.parseLong(claims.getSubject()));
        return user;
    }


    public boolean validateToken(String authToken) {
        try {
            Optional<AuthToken> authTokenOptional = authTokenRepository.findAuthTokenByAccessToken(authToken);
            if (!authTokenOptional.isPresent()) {
                log.error("Token don't exist");
                return false;
            }
            Jwts.parser().setSigningKey(EnvConfig.JWT_SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

}
