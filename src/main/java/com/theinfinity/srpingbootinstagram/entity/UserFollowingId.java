package com.theinfinity.srpingbootinstagram.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowingId implements Serializable {
    @JsonView(Views.Id.class)
    private Long channelId;
    @JsonView(Views.Id.class)
    private Long followerId;
}
