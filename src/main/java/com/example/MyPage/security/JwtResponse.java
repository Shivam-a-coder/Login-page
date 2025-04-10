package com.example.MyPage.security;

public class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
        System.out.println("JWT Token: " + this.token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;

    }
}
