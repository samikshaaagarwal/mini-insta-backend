package com.socialfeed.miniinsta.controller;

import com.socialfeed.miniinsta.entity.Comment;
import com.socialfeed.miniinsta.entity.Post;
import com.socialfeed.miniinsta.entity.User;
import com.socialfeed.miniinsta.repository.CommentRepository;
import com.socialfeed.miniinsta.repository.PostRepository;
import com.socialfeed.miniinsta.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;


@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping
    public Comment addComment(@RequestParam Long userId, @RequestParam Long postId, @RequestBody Comment comment) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    @GetMapping("/{postId}")
    public List<Comment> getComments(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return commentRepository.findByPost(post);
    }
    // Delete a comment by its ID
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentRepository.delete(comment);
        return "Comment deleted successfully";
    }
}
