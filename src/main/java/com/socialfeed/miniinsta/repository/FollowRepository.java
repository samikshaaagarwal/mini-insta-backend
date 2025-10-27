package com.socialfeed.miniinsta.repository;

import com.socialfeed.miniinsta.entity.Follow;
import com.socialfeed.miniinsta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollower(User follower);  // users that I follow
    List<Follow> findByFollowing(User following); // users who follow me
    Optional<Follow> findByFollowerAndFollowing(User follower, User following); // specific relation
}
