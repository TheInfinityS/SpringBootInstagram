package com.theinfinity.srpingbootinstagram.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "usr", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "phoneNumber")
})
@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = {"id","username"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String username;

    @Email
    private String email;

    @JsonView(Views.IdName.class)
    private String imageUrl;

    private Boolean emailVerified;

    @JsonIgnore
    private String password;

    @JsonView(Views.FullProfile.class)
    private String fullName;

    @JsonView(Views.FullProfile.class)
    private String gender;

    @JsonView(Views.FullProfile.class)
    private String locale;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullProfile.class)
    private LocalDateTime lastVisit;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;


    @JsonView(Views.FullProfile.class)
    @OneToMany(
            mappedBy = "follower",
            orphanRemoval = true
    )
    private Set<UserFollowing> followings=new HashSet<>();

    @JsonView(Views.FullProfile.class)
    @OneToMany(
            mappedBy = "channel",
            orphanRemoval = true,
            cascade =CascadeType.ALL
    )
    private Set<UserFollowing> followers=new HashSet<>();

    @JsonView(Views.IdName.class)
    private Boolean isPrivate;
}
