package com.socialfeed.miniinsta.repository;

import com.socialfeed.miniinsta.entity.Post;
import com.socialfeed.miniinsta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Fetch posts for multiple users, sorted by creation time descending
    List<Post> findByUserInOrderByCreatedAtDesc(List<User> users);
}
