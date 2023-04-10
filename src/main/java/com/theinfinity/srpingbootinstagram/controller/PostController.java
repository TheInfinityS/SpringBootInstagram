package com.theinfinity.srpingbootinstagram.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.theinfinity.srpingbootinstagram.dto.PostPage;
import com.theinfinity.srpingbootinstagram.entity.LikeContent;
import com.theinfinity.srpingbootinstagram.entity.Post;
import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.entity.Views;
import com.theinfinity.srpingbootinstagram.repository.PostRepository;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import com.theinfinity.srpingbootinstagram.security.CurrentUser;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import com.theinfinity.srpingbootinstagram.service.PostService;
import com.theinfinity.srpingbootinstagram.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    public static final int  POST_PER_PAGE=3;
    private final PostService postService;
    private final UserRepository userRepository;

    @Autowired
    public PostController(PostRepository postRepository, PostService postService, WsSender wsSender, UserRepository userRepository) {
        this.postService = postService;
        this.userRepository = userRepository;
    }


    @GetMapping
    @JsonView(Views.FullPost.class)
    public PostPage list(@PageableDefault(size = POST_PER_PAGE,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                         @CurrentUser UserPrincipal userPrincipal)
    {
        User user=userRepository.findByUsername(userPrincipal.getUsername()).get();
        return postService.findForUser(pageable, user);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullComment.class)
    public Post getOne(@PathVariable("id") Post post) {
        return postService.getOne(post);
    }


    @PostMapping(headers = {"content-type=multipart/*"})
    @JsonView(Views.FullPost.class)
    public Post create(
            @RequestParam("file") MultipartFile file,
            @RequestParam("text") String text,
            @CurrentUser UserPrincipal userPrincipal
    ) throws IOException {
        Post post=new Post();
        post.setText(text);
        System.out.println(file);
        return postService.create(post,userPrincipal,file);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullPost.class)
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

    @GetMapping("user/{id}")
    @JsonView(Views.FullPost.class)
    public List<Post> getPostsByAuthor(@PathVariable("id") User user){
        return postService.findByUser(user);
    }

    @PostMapping("like/{id}")
    @JsonView(Views.FullPost.class)
    public Post like(
            @PathVariable("id") Post post,
            @CurrentUser UserPrincipal userPrincipal
    ){
        return postService.like(post,userPrincipal);
    }
}
