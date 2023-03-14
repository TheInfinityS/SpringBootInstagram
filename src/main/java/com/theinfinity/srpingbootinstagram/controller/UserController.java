package com.theinfinity.srpingbootinstagram.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.theinfinity.srpingbootinstagram.dto.PostPage;
import com.theinfinity.srpingbootinstagram.entity.Post;
import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.entity.UserFollowing;
import com.theinfinity.srpingbootinstagram.entity.Views;
import com.theinfinity.srpingbootinstagram.security.CurrentUser;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import com.theinfinity.srpingbootinstagram.service.CustomUserService;
import com.theinfinity.srpingbootinstagram.service.PostService;
import com.theinfinity.srpingbootinstagram.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.theinfinity.srpingbootinstagram.controller.PostController.POST_PER_PAGE;

@RestController
@RequestMapping("profile")
public class UserController {
    private final CustomUserService userService;
    private final UserFollowingService userFollowingService;

    private final PostService postService;


    @Autowired
    public UserController(CustomUserService userService, UserFollowingService userFollowingService, PostService postService) {
        this.userService = userService;
        this.userFollowingService = userFollowingService;
        this.postService = postService;
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

    @PostMapping("change-follower/{followerId}")
    @JsonView(Views.FullProfile.class)
    public User changeFollower(@CurrentUser UserPrincipal channel,
                               @PathVariable("followerId") User follower){
        return userService.changeFollower(channel,follower);
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

    @GetMapping("get-followings/{followerId}")
    @JsonView(Views.IdName.class)
    public List<UserFollowing> followings(@PathVariable("followerId") User follower){
        System.out.println(follower);
        return userFollowingService.getFollowing(follower);
    }
}
