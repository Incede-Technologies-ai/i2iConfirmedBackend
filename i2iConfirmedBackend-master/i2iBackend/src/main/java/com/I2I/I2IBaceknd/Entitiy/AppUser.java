package com.I2I.I2IBaceknd.Entitiy;

import com.I2I.I2IBaceknd.Enum.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity

@Table(name = "users")

public class AppUser {

    @Id
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // Getters & setters
}
