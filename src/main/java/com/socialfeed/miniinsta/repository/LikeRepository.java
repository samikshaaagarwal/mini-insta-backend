package com.socialfeed.miniinsta.repository;

import com.socialfeed.miniinsta.entity.Like;
import com.socialfeed.miniinsta.entity.Post;
import com.socialfeed.miniinsta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    List<Like> findByPost(Post post);
}
