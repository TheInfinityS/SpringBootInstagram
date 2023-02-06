package com.theinfinity.srpingbootinstagram.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.theinfinity.srpingbootinstagram.entity.Post;
import com.theinfinity.srpingbootinstagram.entity.Views;
import com.theinfinity.srpingbootinstagram.repository.PostRepository;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import com.theinfinity.srpingbootinstagram.security.CurrentUser;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Post> list() {
        return postRepository.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullPost.class)
    public Post getOne(@PathVariable("id") Post post) {
        return post;
    }

    @PostMapping
    public Post create(@RequestBody Post post, @CurrentUser UserPrincipal userPrincipal) {
        post.setAuthor(userRepository.findByUsername(userPrincipal.getUsername()).get());
        post.setCreationTime(LocalDateTime.now());
        return postRepository.save(post);
    }

    @PutMapping("{id}")
    public Post update(
            @PathVariable("id") Post postFromDb,
            @RequestBody Post post
    ) {
        BeanUtils.copyProperties(post, postFromDb, "id");

        return postRepository.save(postFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Post post) {
        postRepository.delete(post);
    }
}
