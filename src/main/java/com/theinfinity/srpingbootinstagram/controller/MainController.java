package com.theinfinity.srpingbootinstagram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.theinfinity.srpingbootinstagram.dto.PostPage;
import com.theinfinity.srpingbootinstagram.entity.User;
import com.theinfinity.srpingbootinstagram.entity.Views;
import com.theinfinity.srpingbootinstagram.repository.UserRepository;
import com.theinfinity.srpingbootinstagram.security.CurrentUser;
import com.theinfinity.srpingbootinstagram.security.UserPrincipal;
import com.theinfinity.srpingbootinstagram.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

import static com.theinfinity.srpingbootinstagram.controller.PostController.POST_PER_PAGE;

@Controller
@RequestMapping("/")
public class MainController {

    @Value("${spring.profiles.active}")
    private String profile;
    private final UserRepository userRepository;
    private final PostService postService;
    private final ObjectWriter messageWriter;

    private final ObjectWriter userWriter;

    @Autowired
    public MainController(UserRepository userRepository, PostService postService, ObjectMapper mapper) {
        this.userRepository = userRepository;
        this.postService = postService;

        ObjectMapper objectMapper=mapper.setConfig(mapper.getSerializationConfig());

        this.messageWriter = objectMapper
                .writerWithView(Views.FullPost.class);

        this.userWriter = objectMapper
                .writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(Model model, @CurrentUser UserPrincipal userPrincipal) throws JsonProcessingException {

        HashMap<Object,Object> data=new HashMap<>();

        if(userPrincipal!=null){
            User user=userRepository.findByUsername(userPrincipal.getUsername()).get();
            model.addAttribute("profile", userWriter.writeValueAsString(user));

            Sort sort=Sort.by(Sort.Direction.DESC,"id");
            PageRequest pageRequest = PageRequest.of(0, POST_PER_PAGE, sort);
            System.out.println(pageRequest.getPageNumber()+" : "+pageRequest.getPageSize());
            PostPage postPage = postService.findForUser(pageRequest,user);

            String posts = messageWriter.writeValueAsString(postPage.getPosts());

            model.addAttribute("posts", posts);

            data.put("currentPage",postPage.getCurrentPage());
            data.put("totalPages",postPage.getTotalPages());
        }
        else {
            model.addAttribute("messages", "[]");
            model.addAttribute("profile","null");
        }
        model.addAttribute("frontendData",data);
        model.addAttribute("isDevMode","dev".equals(profile));
        return "index";
    }
}
