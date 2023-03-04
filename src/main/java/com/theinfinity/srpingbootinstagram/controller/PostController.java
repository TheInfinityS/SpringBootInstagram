package com.theinfinity.srpingbootinstagram.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.theinfinity.srpingbootinstagram.entity.Post;
import com.theinfinity.srpingbootinstagram.entity.Views;
import com.theinfinity.srpingbootinstagram.repository.PostRepository;
import com.theinfinity.srpingbootinstagram.security.CurrentUser;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import com.theinfinity.srpingbootinstagram.service.PostService;
import com.theinfinity.srpingbootinstagram.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostRepository postRepository, PostService postService, WsSender wsSender) {
        this.postService = postService;
    }


    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Post> list() {
        return postService.findAll();
    }

    @GetMapping("{id}")
    @JsonView({Views.FullPost.class})
    public Post getOne(@PathVariable("id") Post post) {
        return postService.getOne(post);
    }

    @PostMapping
    public Post create(
            @RequestBody Post post,
            @CurrentUser UserPrincipal userPrincipal
//            ,@RequestParam("file") MultipartFile file
    ) throws IOException {
        return postService.create(post,userPrincipal,null);
    }

    @PutMapping("{id}")
    public Post update(
            @PathVariable("id") Post postFromDb,
            @RequestBody Post post,
            @CurrentUser UserPrincipal userPrincipal
    ) {
        return postService.update(postFromDb,post,userPrincipal);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Post post) {
        postService.delete(post);
    }
}
