package com.I2I.I2IBaceknd.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.I2I.I2IBaceknd.Components.JwtUtil;
import com.I2I.I2IBaceknd.Dao.AuthRequest;
import com.I2I.I2IBaceknd.Dao.AuthResponse;
import com.I2I.I2IBaceknd.Dao.AuthResponses;
import com.I2I.I2IBaceknd.Dao.RegisterRequest;
import com.I2I.I2IBaceknd.Entitiy.AppUser;
import com.I2I.I2IBaceknd.Enum.UserRole;
import com.I2I.I2IBaceknd.Repository.UserRepository;
import com.I2I.I2IBaceknd.Service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil,
                          CustomUserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtUtil.generateToken(userDetails.getUsername());

        // Extract roles from UserDetails
        List<String> roles = userDetails.getAuthorities()
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList());

        return ResponseEntity.ok(new AuthResponses(token, roles));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        UserRole userRole;
        try {
            userRole = UserRole.valueOf(request.getRole()); // Convert string to enum
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role. Only ROLE_USER and ROLE_ADMIN are allowed.");
        }

        if (userRepository.existsById(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(userRole);

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }


}

