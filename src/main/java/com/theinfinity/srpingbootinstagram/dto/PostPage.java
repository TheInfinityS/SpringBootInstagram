package com.theinfinity.srpingbootinstagram.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.theinfinity.srpingbootinstagram.entity.Post;
import com.theinfinity.srpingbootinstagram.entity.Views;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonView(Views.FullPost.class)
public class PostPage {
    private List<Post> posts;
    private int currentPage;
    private int totalPages;
}
