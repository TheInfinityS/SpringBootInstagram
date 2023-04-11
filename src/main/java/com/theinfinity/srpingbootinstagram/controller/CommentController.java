package com.theinfinity.srpingbootinstagram.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.theinfinity.srpingbootinstagram.entity.Comment;
import com.theinfinity.srpingbootinstagram.entity.Post;
import com.theinfinity.srpingbootinstagram.entity.Views;
import com.theinfinity.srpingbootinstagram.security.CurrentUser;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import com.theinfinity.srpingbootinstagram.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @JsonView(Views.FullComment.class)
    public Comment create(
            @RequestBody Comment comment,
            @CurrentUser UserPrincipal user
    ) {
        return commentService.create(comment, user);
    }

    @DeleteMapping("{id}")
    @JsonView(Views.FullComment.class)
    public void delete(@PathVariable("id") Comment comment){
        commentService.delete(comment);
    }

    @PostMapping("like/{id}")
    @JsonView(Views.FullComment.class)
    public Comment like(
            @PathVariable("id") Comment comment,
            @CurrentUser UserPrincipal userPrincipal
    ){
        return commentService.like(comment,userPrincipal);
    }
}
