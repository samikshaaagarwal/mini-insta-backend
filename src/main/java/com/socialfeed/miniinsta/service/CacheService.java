package com.socialfeed.miniinsta.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @CacheEvict(value = "feed", key = "#userId")
    public void evictFeedCache(Long userId) {
        System.out.println("🧼 Evicted cache for user " + userId);
    }
}
