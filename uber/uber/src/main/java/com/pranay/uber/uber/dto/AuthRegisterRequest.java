package com.pranay.uber.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AuthRegisterRequest {
    @NotBlank(message = "username is required")
    @Size(min = 3, message = "username must be at least 3 characters")
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 4, message = "password must be at least 4 characters")
    private String password;

    @NotBlank(message = "role is required")
    @Pattern(regexp = "ROLE_USER|ROLE_DRIVER", message = "role must be ROLE_USER or ROLE_DRIVER")
    private String role;

    public AuthRegisterRequest() {}
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
