package com.example.signaturevalidationserver.model;

import com.example.signaturevalidationserver.model.roles.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "username", "id"}))
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING) private UserRole role;
    @Column(name = "is_validated", nullable = false)
    private boolean isValidated;
    @Column(name = "verification_code")
    private String verificationCode;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VerifiedFiles> files = new ArrayList<>();


    public User(String email, String username, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = UserRole.ROLE_USER;
    }

    public User(String email, String username, String password, UserRole role){
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User() {

    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isValidated;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

  }
