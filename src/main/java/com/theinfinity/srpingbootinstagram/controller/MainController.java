package com.theinfinity.srpingbootinstagram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import com.theinfinity.srpingbootinstagram.security.CurrentUser;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String main(Model model, @CurrentUser UserPrincipal userPrincipal) throws JsonProcessingException {
        User cUser=userRepository.findByUsername(userPrincipal.getUsername()).get();
        model.addAttribute("user",cUser);
        return "index";
    }
}
