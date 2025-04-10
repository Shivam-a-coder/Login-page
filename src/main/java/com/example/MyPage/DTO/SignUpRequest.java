package com.example.MyPage.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SignUpRequest {

    @NotBlank
    private String name;
    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$")
    private String password;

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$") String getPassword() {
        return password;
    }

    public void setPassword(@Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$") String password) {
        this.password = password;
    }

    public @Pattern(regexp = "^\\+?[0-9]{10,13}$") String getPhone() {
        return phone;
    }

    public void setPhone(@Pattern(regexp = "^\\+?[0-9]{10,13}$") String phone) {
        this.phone = phone;
    }

    @Pattern(regexp = "^\\+?[0-9]{10,13}$")
    private String phone;
}
