package com.theinfinity.srpingbootinstagram.pojo;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    public AuthResponse(String token) {
        this.token=token;
    }
}
