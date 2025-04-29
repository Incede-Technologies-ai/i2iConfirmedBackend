package com.I2I.I2IBaceknd.Dao;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role; // Will be converted to enum later
}
