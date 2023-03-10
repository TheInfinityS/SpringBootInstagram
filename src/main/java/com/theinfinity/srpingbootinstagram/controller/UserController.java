package com.theinfinity.srpingbootinstagram.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.entity.UserFollowing;
import com.theinfinity.srpingbootinstagram.entity.Views;
import com.theinfinity.srpingbootinstagram.security.CurrentUser;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import com.theinfinity.srpingbootinstagram.service.CustomUserService;
import com.theinfinity.srpingbootinstagram.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("profile")
public class UserController {

    private final CustomUserService userService;
    private final UserFollowingService userFollowingService;

    @Autowired
    public UserController(CustomUserService userService, UserFollowingService userFollowingService) {
        this.userService = userService;
        this.userFollowingService = userFollowingService;
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

    @GetMapping("get-followers/{channelId}")
    @JsonView(Views.IdName.class)
    public List<UserFollowing> followers(@PathVariable("channelId") User channel){
        return userFollowingService.getFollower(channel);
    }

    @PostMapping("change-status/{followerId}")
    @JsonView(Views.IdName.class)
    public UserFollowing changeFollowingStatus(@CurrentUser UserPrincipal channelPrincipal,@PathVariable("followerId") User follower){
        return userFollowingService.changeFollowingStatus(channelPrincipal,follower);
    }

}
