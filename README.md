MiniInsta - Social Feed Backend

MiniInsta is a simple social media backend built using Spring Boot. It allows users to create posts, follow other users, and view a personalized feed. The feed is cached using Caffeine for better performance and automatically updates when new posts are created.

Features

User authentication and role management using JWT

Create and view posts

Follow and unfollow users

Personalized feed showing posts from followed users and the user themself

Feed caching using Spring Cache and Caffeine

Automatic cache invalidation when a new post is created

Tech Stack

Spring Boot

Spring Security with JWT

Spring Data JPA

Caffeine Cache

H2 or MySQL (configurable)

How It Works

When a user requests their feed, the data is first checked in the cache. If it is not available, it is fetched from the database and stored in the cache.
When a user or one of their followed users creates a new post, the relevant feed caches are cleared so that the feed always stays updated.

Future Enhancements

Frontend integration using React or Angular

Like and comment features

Image upload functionality

