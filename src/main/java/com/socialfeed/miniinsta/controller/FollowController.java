package com.socialfeed.miniinsta.controller;

import com.socialfeed.miniinsta.entity.Follow;
import com.socialfeed.miniinsta.entity.User;
import com.socialfeed.miniinsta.repository.FollowRepository;
import com.socialfeed.miniinsta.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/follows")
public class FollowController {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String followUser(@RequestParam Long followerId, @RequestParam Long followingId) {
        User follower = userRepository.findById(followerId).orElseThrow();
        User following = userRepository.findById(followingId).orElseThrow();

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        followRepository.save(follow);

        return follower.getName() + " is now following " + following.getName();
    }

    @DeleteMapping
    public String unfollowUser(@RequestParam Long followerId, @RequestParam Long followingId) {
        User follower = userRepository.findById(followerId).orElseThrow();
        User following = userRepository.findById(followingId).orElseThrow();

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new RuntimeException("Follow relation not found"));

        followRepository.delete(follow);

        return follower.getName() + " unfollowed " + following.getName();
    }

    @GetMapping("/{userId}/following")
    public List<User> getFollowing(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return followRepository.findByFollower(user)
                .stream()
                .map(Follow::getFollowing)
                .toList();
    }

    @GetMapping("/{userId}/followers")
    public List<User> getFollowers(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return followRepository.findByFollowing(user)
                .stream()
                .map(Follow::getFollower)
                .toList();
    }


}
