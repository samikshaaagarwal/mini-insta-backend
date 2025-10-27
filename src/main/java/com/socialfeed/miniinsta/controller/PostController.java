package com.socialfeed.miniinsta.controller;

import com.socialfeed.miniinsta.entity.Post;
import com.socialfeed.miniinsta.entity.Follow;
import com.socialfeed.miniinsta.entity.User;
import com.socialfeed.miniinsta.service.FeedService;
import com.socialfeed.miniinsta.repository.PostRepository;
import com.socialfeed.miniinsta.repository.FollowRepository;
import com.socialfeed.miniinsta.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts") // all post APIs start with /posts
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    private final FeedService feedService;

    @Autowired
    public PostController(FeedService feedService) {
        this.feedService = feedService;
    }

    // 1. Create a new post
    @PostMapping
    public Post createPost(@RequestParam Long userId, @RequestBody Post post) {
        return feedService.createPost(post, userId);
    }

    // 2. Get all posts
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 3. Feed: show posts from all users a given user follows
    @GetMapping("/feed/{userId}")
    public List<Post> getFeed(@PathVariable Long userId) {
        System.out.println("Fetching feed from DB for user " + userId); // log to see DB hits

        return feedService.getFeedForUser(userId);
    }

    // 4. Get posts by a specific user
    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return postRepository.findByUserInOrderByCreatedAtDesc(List.of(user));
    }
}
