package com.myKart.security.gateway.util; // Or your equivalent package

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private String secret;
    private SecretKey key; // This will hold our decoded key

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
        // Decode the Base64 string from config into a secure Key object
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // Check if the token has expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract all claims using the new builder
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(key) // Use the Key object
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    // Generic claim extractor
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}