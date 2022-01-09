package com.example.cnpmbe.jwt;

import com.example.cnpmbe.model.entity.auth.User;
import com.example.cnpmbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenUserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsernameAndIsDeletedIsFalse(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(userOptional.get());
        return customUserDetails;
    }

    public UserDetails loadUserById(Long  id){
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return null;
        }
        return new CustomUserDetails(userOptional.get());
    }




}
