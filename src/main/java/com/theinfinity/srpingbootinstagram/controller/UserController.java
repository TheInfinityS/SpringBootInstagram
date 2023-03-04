package com.theinfinity.srpingbootinstagram.controller;

import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.exception.ResourceNotFoundException;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getUser(@PathVariable("username") UserPrincipal userPrincipal) {
        return userRepository.findByUsername(userPrincipal.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
