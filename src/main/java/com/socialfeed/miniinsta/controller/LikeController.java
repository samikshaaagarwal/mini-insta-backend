package com.socialfeed.miniinsta.controller;

import com.socialfeed.miniinsta.entity.Like;
import com.socialfeed.miniinsta.entity.Post;
import com.socialfeed.miniinsta.entity.User;
import com.socialfeed.miniinsta.repository.LikeRepository;
import com.socialfeed.miniinsta.repository.PostRepository;
import com.socialfeed.miniinsta.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping
    public String likePost(@RequestParam Long userId, @RequestParam Long postId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        if (likeRepository.findByUserAndPost(user, post).isPresent()) {
            return "Already liked!";
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);

        return user.getName() + " liked post " + post.getId();
    }

    @DeleteMapping
    public String unlikePost(@RequestParam Long userId, @RequestParam Long postId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new RuntimeException("Like not found"));
        likeRepository.delete(like);

        return user.getName() + " unliked post " + post.getId();
    }

    @GetMapping("/{postId}")
    public int getLikes(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return likeRepository.findByPost(post).size();
    }
}
