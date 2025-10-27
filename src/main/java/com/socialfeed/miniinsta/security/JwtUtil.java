package com.socialfeed.miniinsta.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    // Generate a strong secret key (HS256)
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Token validity (1 hour here)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    // Generate token with email and role
    public static String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email) // subject = email
                .addClaims(Map.of("role", role)) // custom claim = role
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Extract all claims from token
    public static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract email (subject)
    public static String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // Extract role
    public static String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }
}
