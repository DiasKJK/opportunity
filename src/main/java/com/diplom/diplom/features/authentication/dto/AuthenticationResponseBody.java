package com.diplom.diplom.features.authentication.dto;

public class AuthenticationResponseBody {
    private final String token;
    private final String message;
    private final String role;

    public AuthenticationResponseBody(String token, String message, String role) {
        this.token = token;
        this.message = message;
        this.role = role;
    }

    public String getToken() {
        return token;
    }
    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }
}
