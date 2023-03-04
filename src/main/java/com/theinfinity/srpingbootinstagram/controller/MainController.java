package com.theinfinity.srpingbootinstagram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.entity.Views;
import com.theinfinity.srpingbootinstagram.repository.PostRepository;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import com.theinfinity.srpingbootinstagram.security.CurrentUser;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    @Value("${spring.profiles.active}")
    private String profile;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ObjectWriter writer;

    @Autowired
    public MainController(UserRepository userRepository, PostRepository postRepository, ObjectMapper mapper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.writer = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullPost.class);
    }

    @GetMapping
    public String main(Model model, @CurrentUser UserPrincipal userPrincipal) throws JsonProcessingException {

        HashMap<Object,Object> data=new HashMap<>();

        if(userPrincipal!=null){
            User user=userRepository.findByUsername(userPrincipal.getUsername()).get();
            data.put("profile",user);
            String posts = writer.writeValueAsString(postRepository.findAll());
            model.addAttribute("posts", posts);
        }
        else {
            model.addAttribute("messages", "[]");
        }
        model.addAttribute("frontendData",data);
        model.addAttribute("isDevMode","dev".equals(profile));
        return "index";
    }
}
