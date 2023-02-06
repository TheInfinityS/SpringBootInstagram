package com.theinfinity.srpingbootinstagram.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@ToString(of={"id","text"})
@EqualsAndHashCode(of={"id"})
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String text;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullPost.class)
    private LocalDateTime creationTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullPost.class)
    private User author;

    @OneToMany(mappedBy = "post",orphanRemoval = true)
    @JsonView(Views.FullPost.class)
    private List<Comment> comments;

    @JsonView(Views.IdName.class)
    private String imageUrl;
}
