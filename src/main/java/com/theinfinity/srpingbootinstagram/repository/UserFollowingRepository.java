package com.theinfinity.srpingbootinstagram.repository;

import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.entity.UserFollowing;
import com.theinfinity.srpingbootinstagram.entity.UserFollowingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFollowingRepository extends JpaRepository<UserFollowing, UserFollowingId> {
    List<UserFollowing> findByFollower(User user);
}
