package com.theinfinity.srpingbootinstagram.controller;

import com.theinfinity.srpingbootinstagram.entity.Role;
import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(("/registration"))
public class RegistrationClontroller {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public User registration(@RequestBody User user)
    {
        return user;
    }

    @PostMapping
    public User addUser(@RequestBody User user)
    {
        user.setRole(Role.USER);
        user.setLastVisit(LocalDateTime.now());
        return userRepository.save(user);
    }
}
