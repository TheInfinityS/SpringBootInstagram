package com.theinfinity.srpingbootinstagram.service;

import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.entity.UserFollowing;
import com.theinfinity.srpingbootinstagram.repository.UserFollowingRepository;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFollowingService {
    private final UserFollowingRepository userFollowingRepository;
    private final UserRepository userRepository;

    public UserFollowingService(UserFollowingRepository userFollowingRepository, UserRepository userRepository) {
        this.userFollowingRepository = userFollowingRepository;
        this.userRepository = userRepository;
    }

    public List<UserFollowing> getFollower(User channel) {
        return userFollowingRepository.findByChannel(channel);
    }


    public UserFollowing changeFollowingStatus(UserPrincipal channelPrincipal, User follower) {
        User channel=userRepository.findByUsername(channelPrincipal.getUsername()).get();
        UserFollowing following=userFollowingRepository.findByChannelAndFollower(channel,follower);
        following.setActive(!following.isActive());
        return userFollowingRepository.save(following);
    }
}
