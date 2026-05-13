package com.devpeepu.peepubank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devpeepu.peepubank.dto.SignupRequest;
import com.devpeepu.peepubank.entity.User;
import com.devpeepu.peepubank.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // LOGIN (now uses DB)
    public boolean loginUser(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(password, user.getPassword());
    }

    // REGISTER
    public boolean registerUser(SignupRequest req, String confirmPassword) {

        // check password match
        if (!req.getPassword().equals(confirmPassword)) {
            return false;
        }

        // check duplicate username
        if (userRepository.findByUsername(req.getUsername()) != null) {
            return false;
        }

        // create user
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setEmail(req.getEmail());
        user.setName(req.getName());

        // save to DB
        userRepository.save(user);

        return true;
    }
}