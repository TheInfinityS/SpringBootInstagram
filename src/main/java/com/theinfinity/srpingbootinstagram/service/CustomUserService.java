package com.theinfinity.srpingbootinstagram.service;

import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.entity.UserFollowing;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
        List<UserFollowing> followings=channel.getFollowers().stream().filter(following->
                        following.getFollower().equals(uFollower))
                .collect(Collectors.toList());
        if(followings.isEmpty()){
            UserFollowing userFollowing = new UserFollowing(channel,uFollower);
            channel.getFollowers().add(userFollowing);
        }
        else {
            channel.getFollowers().removeAll(followings);
        }
        return userRepository.save(channel);
    }
}
