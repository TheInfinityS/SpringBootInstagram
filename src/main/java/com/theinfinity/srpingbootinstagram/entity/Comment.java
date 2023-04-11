package com.theinfinity.srpingbootinstagram.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
@EqualsAndHashCode(of = {"id"})
public class Comment {
    @Id
    @GeneratedValue
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonView(Views.FullPost.class)
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false,updatable = false)
    @JsonView(Views.IdName.class)
    private User author;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullComment.class)
    private LocalDateTime creationTime;

    @OneToMany(mappedBy = "contentid", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonView(Views.FullPost.class)
    private Set<LikeContent> likes=new HashSet<>();
}
