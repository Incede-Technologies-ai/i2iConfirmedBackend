package com.I2I.I2IBaceknd.Components;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Generate a secure key for HMAC SHA-256
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    private final long expiration = 1000 * 60 * 60;

    @SuppressWarnings("deprecation")

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)  // Sign with key directly
                .compact();
    }

    // Extract username (subject) from the token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate the token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Can also log e.getMessage() if needed
            return false;
        }
    }
}
