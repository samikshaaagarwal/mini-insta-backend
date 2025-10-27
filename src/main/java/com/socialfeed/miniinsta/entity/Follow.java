package com.socialfeed.miniinsta.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user who follows
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    // The user being followed
    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;
}
