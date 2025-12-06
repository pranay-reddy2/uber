package com.pranay.uber.service;

import com.pranay.uber.model.User;
import com.pranay.uber.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo) { this.repo = repo; }

    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public Optional<User> findById(String id) {
        return repo.findById(id);
    }
}
