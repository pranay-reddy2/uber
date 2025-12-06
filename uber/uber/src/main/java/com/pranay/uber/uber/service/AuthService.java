package com.pranay.uber.service;

import com.pranay.uber.dto.AuthLoginRequest;
import com.pranay.uber.dto.AuthRegisterRequest;
import com.pranay.uber.dto.AuthResponse;
import com.pranay.uber.exception.BadRequestException;
import com.pranay.uber.model.User;
import com.pranay.uber.repository.UserRepository;
import com.pranay.uber.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(AuthRegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new BadRequestException("username already exists");
        }
        String hashed = passwordEncoder.encode(req.getPassword());
        User u = new User(req.getUsername(), hashed, req.getRole());
        userRepository.save(u);
        String token = jwtUtil.generateToken(u.getUsername(), u.getRole());
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthLoginRequest req) {
        User u = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new BadRequestException("invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), u.getPassword())) {
            throw new BadRequestException("invalid credentials");
        }
        String token = jwtUtil.generateToken(u.getUsername(), u.getRole());
        return new AuthResponse(token);
    }
}
