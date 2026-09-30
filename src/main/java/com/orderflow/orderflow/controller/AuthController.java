package com.orderflow.orderflow.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.orderflow.orderflow.User;
import com.orderflow.orderflow.dto.AuthRequest;
import com.orderflow.orderflow.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.orderflow.orderflow.security.JwtService;
import com.orderflow.orderflow.dto.AuthResponse;

@RestController
public class AuthController {
	private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

this.userRepository = userRepository;
this.passwordEncoder = passwordEncoder;
this.jwtService = jwtService;
}

    @PostMapping("/auth/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {

        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                authRequest.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole()
        );

        return new AuthResponse(token);
    }
}