package com.example.signaturevalidationserver.service;


import com.example.signaturevalidationserver.model.User;
import com.example.signaturevalidationserver.model.exceptions.DuplicateEmailException;
import com.example.signaturevalidationserver.model.exceptions.DuplicateUsernameException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    String encodePassword(String password);
    User saveUser(String email, String username, String password) throws DuplicateEmailException, DuplicateUsernameException;
    String generateRandomString(int length);
    void setVerificationCode(User user, String code);
    void sendVerificationCode(User user);
    User findByCode(String code);
    void setIsValidated(User user);
    boolean validateUsernameAndPasswordResetToken(String username, String token);
    void changePassword(String username,String password);
    User findByUsername(String username);
    User getCurrentUser();

    void sendPasswordResetToken(String username);
    void cleanPasswordToken(String username);
    List<User> getAllUsersWithoutRole();
    void setRole(String username, String role);
    String geUserEmail(String username);


}