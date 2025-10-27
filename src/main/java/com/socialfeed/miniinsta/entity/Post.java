package com.socialfeed.miniinsta.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Many posts can belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
