package com.theinfinity.srpingbootinstagram.service;

import com.theinfinity.srpingbootinstagram.dto.EventType;
import com.theinfinity.srpingbootinstagram.dto.ObjectType;
import com.theinfinity.srpingbootinstagram.entity.Comment;
import com.theinfinity.srpingbootinstagram.entity.Views;
import com.theinfinity.srpingbootinstagram.repository.CommentRepository;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import com.theinfinity.srpingbootinstagram.util.WsSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepository commentRepo;
    private final UserRepository userRepository;

    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepository commentRepo, UserRepository userRepository, WsSender wsSender) {
        this.commentRepo = commentRepo;
        this.userRepository = userRepository;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.IdName.class);
    }

    public Comment create(Comment comment, UserPrincipal user) {
        comment.setAuthor(userRepository.findByUsername(user.getUsername()).get());
        comment.setCreationTime(LocalDateTime.now());
        comment.setLikes(0);
        Comment commentFromDb=commentRepo.save(comment);
        wsSender.accept(EventType.CREATE,commentFromDb);
        return commentFromDb;
    }

    public void delete(Comment comment) {

        commentRepo.delete(comment);
        wsSender.accept(EventType.REMOVE,comment);
    }
}
