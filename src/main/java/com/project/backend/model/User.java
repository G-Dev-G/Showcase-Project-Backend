package com.project.backend.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "TheUser", uniqueConstraints = {@UniqueConstraint(columnNames = "usernameEmail")})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // auto generate ID
    @Column(updatable = false)
    private Long userId; // primary key

    private String name;
    private String usernameEmail;
    private String address;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole; // USER/ADMIN

    public User() {
    }

    public User(Long userId, String name, String usernameEmail, String address, String password, UserRole userRole) {
        this.userId = userId;
        this.name = name;
        this.usernameEmail = usernameEmail;
        this.address = address;
        this.password = password;
        this.userRole = userRole;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsernameEmail() {
        return usernameEmail;
    }

    public void setUsernameEmail(String usernameEmail) {
        this.usernameEmail = usernameEmail;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    // =========== Spring Security UserDetails Override =========================
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return usernameEmail;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
