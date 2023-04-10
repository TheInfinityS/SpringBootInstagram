package com.theinfinity.srpingbootinstagram.service;

import com.theinfinity.srpingbootinstagram.entity.Content;
import com.theinfinity.srpingbootinstagram.entity.LikeContent;
import com.theinfinity.srpingbootinstagram.entity.Post;
import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public LikeContent like(Content content, Long contentId, User user) {
        return likeRepository.save(new LikeContent(content,contentId,user));
    }
}
