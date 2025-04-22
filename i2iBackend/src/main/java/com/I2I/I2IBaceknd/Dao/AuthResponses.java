package com.I2I.I2IBaceknd.Dao;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponses {
    private String token;
    private List<String> roles;

  

    // Getters and Setters
}
