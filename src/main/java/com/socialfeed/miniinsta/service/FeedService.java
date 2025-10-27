package com.socialfeed.miniinsta.service;

import com.socialfeed.miniinsta.entity.Follow;
import com.socialfeed.miniinsta.entity.Post;
import com.socialfeed.miniinsta.entity.User;
import com.socialfeed.miniinsta.repository.FollowRepository;
import com.socialfeed.miniinsta.repository.PostRepository;
import com.socialfeed.miniinsta.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FeedService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final CacheService cacheService;

    public FeedService(PostRepository postRepository,
                       UserRepository userRepository,
                       FollowRepository followRepository,
                       CacheService cacheService, CacheService cacheService1) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.cacheService = cacheService;
    }

    // ✅ Cache feed per user
    @Cacheable(cacheNames = "feed", key = "#userId")
    public List<Post> getFeedForUser(Long userId) {
        System.out.println("🌀 Fetching feed from DB for user " + userId);
        System.out.println("🔥 getFeedForUser() called — cache miss");


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get followed users + self
        List<Follow> following = followRepository.findByFollower(user);
        List<User> usersForFeed = new ArrayList<>();
        usersForFeed.add(user); // include own posts
        for (Follow f : following) {
            usersForFeed.add(f.getFollowing());
        }

        // Fetch posts of all these users
        return postRepository.findByUserInOrderByCreatedAtDesc(usersForFeed);
    }

    // ✅ Create post and invalidate related caches
    public Post createPost(Post post, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);
        Post savedPost = postRepository.save(post);

        // ❌ Evict cached feeds for all followers (and self)
        List<Follow> followers = followRepository.findByFollowing(user);
        for (Follow f : followers) {
            cacheService.evictFeedCache(f.getFollower().getId());
        }
        cacheService.evictFeedCache(user.getId()); // clear own feed too

        return savedPost;
    }

    // ✅ Used by @CacheEvict to clear user feed
    @CacheEvict(value = "feed", key = "#userId")
    public void evictFeedCache(Long userId) {
        // Spring automatically removes the cache entry
        System.out.println("🧼 Evicted cache for user " + userId);
    }
}
