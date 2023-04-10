package com.theinfinity.srpingbootinstagram.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@Entity
@EqualsAndHashCode(of = { "content","contentid","user"})
@ToString(of = { "content","contentid","user"})
@NoArgsConstructor
public class LikeContent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Enumerated(EnumType.STRING)
    private Content content;

    @JsonView(Views.IdName.class)
    private Long contentid;


    @ManyToOne
    @JsonView(Views.IdName.class)
    private User user;

    public LikeContent(Content content, Long contentid, User user) {
        this.content = content;
        this.contentid = contentid;
        this.user = user;
    }
}
