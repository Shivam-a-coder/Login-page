package com.example.MyPage.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class LoginRequest {
    @Email
    private String email;
    @Pattern(regexp = "^\\+?[0-9]{10,13}$")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
