package com.theinfinity.srpingbootinstagram.controller;


import com.theinfinity.srpingbootinstagram.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {
    public String get(@AuthenticationPrincipal User user){
        return user.getEmail();
    }

}
