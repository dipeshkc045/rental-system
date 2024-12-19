package com.bookrental.api.user.service.impl;

import com.bookrental.api.user.model.User;
import com.bookrental.api.user.model.requestDto.UserSignInDto;
import com.bookrental.api.user.repository.UserRepository;
import com.bookrental.api.user.service.UserService;
import com.bookrental.config.security.JWTService;
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
        User savedUser = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .address(user.getAddress())
                .mobileNo(user.getMobileNo())
                .isActive(user.isActive())
                .build();
        return userRepository.save(savedUser);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByEmail(username);
    }


    @Override
    public String verify(UserSignInDto userSignInDto) {

        User storedUser = userRepository.findByEmail(userSignInDto.getEmail());

        if (passwordEncoder.matches(userSignInDto.getPassword(), storedUser.getPassword())) {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userSignInDto.getEmail(), userSignInDto.getPassword())
            );
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(userSignInDto.getEmail());
            }
        }
        return "fail";
    }
}
