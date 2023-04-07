package com.theinfinity.srpingbootinstagram.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@NoArgsConstructor
public class LikeContent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Content content;
    private Long contentid;


    @ManyToOne
    private User user;

    public LikeContent(Content content, Long contentid, User user) {
        this.content = content;
        this.contentid = contentid;
        this.user = user;
    }
}
