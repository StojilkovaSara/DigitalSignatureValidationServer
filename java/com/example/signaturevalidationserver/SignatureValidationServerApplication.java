package com.example.signaturevalidationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Properties;

@SpringBootApplication(scanBasePackages = "com.example.signaturevalidationserver")
public class SignatureValidationServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(SignatureValidationServerApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("testereden887@gmail.com");
        mailSender.setPassword("tnszeaqovdxtexyn");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
    @Bean
    public KeyStore keyStore() throws Exception{
        String keystorePath = "C:\\Users\\Sara\\IdeaProjects\\SignatureValidationServer\\src\\main\\resources\\keystore.p12";
        String keystorePassword = "Sara1234!";

        try (FileInputStream fis = new FileInputStream(keystorePath)) {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(fis, keystorePassword.toCharArray());
            return keyStore;
        }
    }

}
