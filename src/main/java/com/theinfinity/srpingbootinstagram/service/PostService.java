package com.theinfinity.srpingbootinstagram.service;

import com.theinfinity.srpingbootinstagram.dto.EventType;
import com.theinfinity.srpingbootinstagram.dto.ObjectType;
import com.theinfinity.srpingbootinstagram.dto.PostPage;
import com.theinfinity.srpingbootinstagram.entity.*;
import com.theinfinity.srpingbootinstagram.repository.PostRepository;
import com.theinfinity.srpingbootinstagram.repository.UserFollowingRepository;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import com.theinfinity.srpingbootinstagram.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserFollowingRepository userFollowingRepository;

    private final LikeService likeService;
    private final BiConsumer<EventType,Post> wsSender;


    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, UserFollowingRepository userFollowingRepository, LikeService likeService, WsSender wsSender) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.userFollowingRepository = userFollowingRepository;
        this.likeService = likeService;
        this.wsSender = wsSender.getSender(ObjectType.POST, Views.FullPost.class);
    }

    public PostPage findForUser(Pageable pageable, User user) {
        List<User> channels=userFollowingRepository.findByFollower(user).stream().filter(UserFollowing::isActive).map(UserFollowing::getChannel).collect(Collectors.toList());
        channels.add(user);
        Page<Post> page =postRepository.findByAuthorIn(channels,pageable);
        return new PostPage(page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages());
    }

    public List<Post> findByUser(User user) {
        List<Post> posts=postRepository.findByAuthor(user);
        return posts;
    }

    public Post getOne(Post post) {
        return post;
    }

    public Post create(Post post, UserPrincipal user, MultipartFile file) throws IOException {
        post.setAuthor(userRepository.findByUsername(user.getUsername()).orElse(userRepository.findByUsername("Sam").get()));
        File uploadDir=new File(uploadPath);
        System.out.println(uploadDir.exists());
        if(!uploadDir.exists())
            uploadDir.mkdir();
        if(file!=null) {
            String uuidFile = UUID.randomUUID().toString();
            String fileName = uuidFile + "." + file.getOriginalFilename();
            System.out.println(fileName);
            file.transferTo(new File(uploadPath+"/"+fileName));
            post.setImageUrl(fileName);
        }
        post.setCreationTime(LocalDateTime.now());
        Post updatedPost=postRepository.save(post);
        wsSender.accept(EventType.CREATE,updatedPost);
        return updatedPost;
    }

    public Post update(Post postFromDb, Post post,UserPrincipal userPrincipal){
        post.setAuthor(userRepository.findByUsername(userPrincipal.getUsername()).get());
        BeanUtils.copyProperties(post, postFromDb, "id");
        Post updatedPost=postRepository.save(postFromDb);
        wsSender.accept(EventType.UPDATE,updatedPost);

        return updatedPost;
    }

    public void delete(Post post){
        postRepository.delete(post);
        wsSender.accept(EventType.REMOVE,post);
    }


    public LikeContent like(Post post, UserPrincipal userPrincipal) {
        User user=userRepository.findByUsername(userPrincipal.getUsername()).get();
        return likeService.like(Content.POST,post.getId(),user);
    }
}
