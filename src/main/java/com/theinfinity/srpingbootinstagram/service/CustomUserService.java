package com.theinfinity.srpingbootinstagram.service;

import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.entity.UserFollowing;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User changeFollowing(User channel, UserPrincipal follower) {
        User uFollower=userRepository.findByUsername(follower.getUsername()).get();
        return changeFollowingAndFollower(channel,uFollower);
    }

    public User changeFollower(UserPrincipal channel, User follower) {
        User uChannel=userRepository.findByUsername(channel.getUsername()).get();
        return changeFollowingAndFollower(uChannel,follower);
    }

    public User changeFollowingAndFollower(User channel,User follower){
        List<UserFollowing> followings=channel.getFollowers().stream().filter(following->
                        following.getFollower().equals(follower))
                .collect(Collectors.toList());
        if(followings.isEmpty()){
            UserFollowing userFollowing = new UserFollowing(channel,follower);
            if(channel.getIsPrivate()){
                userFollowing.setActive(false);
            }
            else userFollowing.setActive(true);
            channel.getFollowers().add(userFollowing);
        }
        else {
            channel.getFollowers().removeAll(followings);
        }
        return userRepository.save(channel);
    }
}
