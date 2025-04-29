package com.I2I.I2IBaceknd.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.I2I.I2IBaceknd.Entitiy.AppUser;

public interface UserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);
}

