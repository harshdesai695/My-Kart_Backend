package com.mykart.seller.util; 

import com.mykart.seller.dto.Seller;
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
    private SecretKey key; 

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    public String generateToken(Seller seller) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", seller.getSellerId()); // Gateway expects "userId" in claims
        claims.put("role", "SELLER");
        claims.put("email", seller.getSellerEmail());
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(seller.getSellerName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(key, SignatureAlgorithm.HS512) 
                .compact();
    }
}
