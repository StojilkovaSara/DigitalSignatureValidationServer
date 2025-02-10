package com.example.signaturevalidationserver.repository;

import com.example.signaturevalidationserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findUserByVerificationCode(String code);
    ArrayList<User> findAllByRoleNull();
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

    User findByEmail(String s);
}
