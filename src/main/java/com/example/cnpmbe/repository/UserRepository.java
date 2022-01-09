package com.example.cnpmbe.repository;


import com.example.cnpmbe.model.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsernameAndIsDeletedIsFalse(String username);

    List<User> findAllByIsDeletedIsFalse();

    Optional<User> findUserById(Long id);
}