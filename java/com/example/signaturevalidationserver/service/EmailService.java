package com.example.signaturevalidationserver.service;

public interface EmailService {

    void sendEmail(String to, String subject, String content);
    void sendPasswordResetEmail(String email, String token);
    String getPasswordResetContent(String token);
}
