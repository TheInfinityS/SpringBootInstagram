package com.theinfinity.srpingbootinstagram.service;

import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User changeFollowing(User channel, UserPrincipal follower) {
        User uFollower=userRepository.findByUsername(follower.getUsername()).get();
        if(uFollower.equals(channel)){
            return channel;
        }
        else{
            Set<User> followers=channel.getFollowers();
            if(followers.contains(uFollower)){
                followers.remove(uFollower);
            }
            else {
                followers.add(uFollower);
            }
            return userRepository.save(channel);
        }
    }
}
