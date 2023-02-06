package com.theinfinity.srpingbootinstagram.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @JsonView(Views.FullComment.class)
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false,updatable = false)
    @JsonView(Views.FullComment.class)
    private User author;
}
