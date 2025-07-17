package com.rezumei.rezumei_backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    // Single-line 64-byte Base64-encoded key
    private static final String SECRET_KEY = "fndShw7LJPtnJjFAjCJO60UlOfOaiQJGpTOfQERtzyY7DjIJrmlNsddK9EaXpDm3/hEnBXtlNbDHD9xmPg2kmA==";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
    private final long expirationMs = 1000 * 60 * 60 * 10; // 10 hours

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey)
                .compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}