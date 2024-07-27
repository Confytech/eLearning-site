package com.example.demo.Models;


import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    public void setPassword(String encode) {
    }

    public CharSequence getPassword() {
        return null;
    }
    // Getters and Setters
}
