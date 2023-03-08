package com.theinfinity.srpingbootinstagram.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@NoArgsConstructor
public class UserFollowing implements Serializable {
    @EmbeddedId
    @JsonIgnore
    private UserFollowingId id;

    @MapsId("channelId")
    @ManyToOne
    @JsonView(Views.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private User channel;


    @MapsId("followerId")
    @ManyToOne
    @JsonView(Views.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private User follower;

    @JsonView(Views.IdName.class)
    private boolean active;

    public UserFollowing(User channel, User follower) {
        this.channel = channel;
        this.follower = follower;
        this.id= new UserFollowingId(channel.getId(),follower.getId());
    }
}
