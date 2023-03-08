package com.theinfinity.srpingbootinstagram.service;

import com.theinfinity.srpingbootinstagram.dto.EventType;
import com.theinfinity.srpingbootinstagram.dto.ObjectType;
import com.theinfinity.srpingbootinstagram.dto.PostPage;
import com.theinfinity.srpingbootinstagram.entity.Post;
import com.theinfinity.srpingbootinstagram.entity.Views;
import com.theinfinity.srpingbootinstagram.repository.PostRepository;
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
import java.util.UUID;
import java.util.function.BiConsumer;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final BiConsumer<EventType,Post> wsSender;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, WsSender wsSender) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.wsSender = wsSender.getSender(ObjectType.POST, Views.IdName.class);
    }

    public PostPage findAll(Pageable pageable) {
        Page<Post> page =postRepository.findAll(pageable);
        return new PostPage(page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages());
    }

    public Post getOne(Post post) {
        return post;
    }

    public Post create(Post post, UserPrincipal user, MultipartFile file) throws IOException {
        post.setAuthor(userRepository.findByUsername(user.getUsername()).orElse(userRepository.findByUsername("Sam").get()));
        File uploadDir=new File(uploadPath);
        if(!uploadDir.exists())
            uploadDir.mkdir();
        if(file!=null) {
            String uuidFile = UUID.randomUUID().toString();
            String fileName = uuidFile + "." + file.getOriginalFilename();
            System.out.println(fileName);
            file.transferTo(new File(fileName));
            post.setImageUrl(fileName);
        }
        post.setCreationTime(LocalDateTime.now());
        post.setLikes(0);
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

}
