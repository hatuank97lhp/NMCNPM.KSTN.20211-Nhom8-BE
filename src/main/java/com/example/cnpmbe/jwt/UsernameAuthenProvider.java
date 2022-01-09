package com.example.cnpmbe.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UsernameAuthenProvider implements AuthenticationProvider {

    @Autowired
    private AuthenUserService authenUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String secret = (String) authentication.getCredentials();
        UserDetails user = authenUserService.loadUserByUsername(username);
        if (!(user instanceof CustomUserDetails)) {
            throw new BadCredentialsException("Bad credential: " + username);
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) user;
        if (!passwordEncoder.matches(secret, user.getPassword()))
            throw new BadCredentialsException("Wrong password");

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(customUserDetails, null, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
