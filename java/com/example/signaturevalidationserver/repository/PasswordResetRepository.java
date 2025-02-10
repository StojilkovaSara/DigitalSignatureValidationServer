package com.example.signaturevalidationserver.repository;

import com.example.signaturevalidationserver.model.PasswordReset;
import com.example.signaturevalidationserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
    Optional<PasswordReset> findByUser(User user);
}