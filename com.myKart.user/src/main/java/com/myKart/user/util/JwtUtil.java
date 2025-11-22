package com.myKart.user.util; // Or your equivalent package

import com.myKart.user.dto.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final long TOKEN_VALIDITY = 10 * 60 * 60 * 1000; // 10 hours

    private String secret;
    private SecretKey key; // This will hold our decoded key

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
        // Decode the Base64 string from config into a secure Key object
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("userName", user.getUserName());
        claims.put("email", user.getEmail());
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUserName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                // Use the Key object, not the raw string
                .signWith(key, SignatureAlgorithm.HS512) 
                .compact();
    }
}