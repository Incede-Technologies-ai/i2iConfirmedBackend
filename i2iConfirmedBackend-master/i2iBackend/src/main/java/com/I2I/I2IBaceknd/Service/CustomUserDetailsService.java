package com.I2I.I2IBaceknd.Service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.I2I.I2IBaceknd.Entitiy.AppUser;
import com.I2I.I2IBaceknd.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Get the enum name and strip the "ROLE_" prefix
        String roleName = user.getRole().name().replace("ROLE_", "");

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roleName) // Spring will add ROLE_ automatically
                .build();
    }

}

