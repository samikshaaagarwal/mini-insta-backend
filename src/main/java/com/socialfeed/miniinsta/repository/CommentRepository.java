package com.socialfeed.miniinsta.repository;

import com.socialfeed.miniinsta.entity.Comment;
import com.socialfeed.miniinsta.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}

