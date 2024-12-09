package com.bookrental.api.security.service.impl;

import com.bookrental.config.security.JWTService;
import com.bookrental.api.security.model.User;
import com.bookrental.api.security.model.UserRegistrationDto;
import com.bookrental.api.security.repository.UserRepository;
import com.bookrental.api.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImplementation implements UserService {
    private final JWTService jwtService;
    private final AuthenticationManager authManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        User savedUser= User.builder().email(user.getEmail()).name(user.getName()).isActive(user.isActive()).password(passwordEncoder.encode(user.getPassword())).build();
        return userRepository.save(savedUser);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.findByEmail(registrationDto.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .name(registrationDto.getUsername())
                .password(passwordEncoder.encode(registrationDto.getPassword()))

                .build();
        return userRepository.save(user);
    }

    @Override
    public String verify(User user) {

        User storedUser = userRepository.findByEmail(user.getEmail());

        if (passwordEncoder.matches(user.getPassword(), storedUser.getPassword())) {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getEmail());
            }
        }
        return "fail";
    }
}
