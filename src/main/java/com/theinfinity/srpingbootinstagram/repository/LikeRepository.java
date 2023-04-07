package com.theinfinity.srpingbootinstagram.repository;

import com.theinfinity.srpingbootinstagram.entity.LikeContent;
import com.theinfinity.srpingbootinstagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<LikeContent,Long> {

    List<LikeContent> findByUserIn(List<User> user);
    List<LikeContent> findByContentid(Long id);
}
