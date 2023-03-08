package com.theinfinity.srpingbootinstagram.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.entity.Views;
import com.theinfinity.srpingbootinstagram.security.CurrentUser;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import com.theinfinity.srpingbootinstagram.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
public class UserController {

    private final CustomUserService userService;

    @Autowired
    public UserController(CustomUserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User get(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping("change-following/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeFollowing(@CurrentUser UserPrincipal follower,
                                @PathVariable("channelId") User channel){
        return userService.changeFollowing(channel,follower);
    }

}
