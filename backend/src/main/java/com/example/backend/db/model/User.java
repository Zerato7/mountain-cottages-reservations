package com.example.backend.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED,
        DEACTIVATED
    }

    public enum Type {
        ADMIN,
        TOURIST,
        HOST
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type userType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Type getUserType() {
        return userType;
    }

    public void setUserType(Type userType) {
        this.userType = userType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isAdmin() {
        return Type.ADMIN.equals(this.userType);
    }

    public boolean isNonadmin() {
        return Type.TOURIST.equals(this.userType)
            || Type.HOST.equals(this.userType);
    }

}
