package com.myKart.security.gateway.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.myKart.security.gateway.util.JwtUtil;

import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationFilter implements GlobalFilter {

    @Autowired
    private JwtUtil jwtUtil;

    // Define public endpoints that don't need a token
    private final List<String> publicEndpoints = List.of(
            "/user/login",
            "/user/addUser",
            "/seller/login",
            "/seller/addSeller",
            "/seller/get",
            "/product",
            "/eureka"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 1. Check if the endpoint is public
        if (isPublic(request.getURI().getPath())) {
            return chain.filter(exchange);
        }

        // 2. Check for the Authorization header
        if (!request.getHeaders().containsKey("Authorization")) {
            return this.onError(exchange, "Authorization header is missing", HttpStatus.UNAUTHORIZED);
        }

        String authHeader = request.getHeaders().get("Authorization").get(0);
        System.out.print("auth:"+authHeader);

        // 3. Check if it's a Bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        // Remove "Bearer "
        System.out.print("token:"+token);
        // 4. Validate the token
        if (!jwtUtil.validateToken(token)) {
            return this.onError(exchange, "JWT token is invalid or expired", HttpStatus.UNAUTHORIZED);
        }

        // 5. (OPTIONAL BUT RECOMMENDED) Pass user info to downstream services
        Claims claims = jwtUtil.extractAllClaims(token);
        String userId = claims.get("userId").toString();

        // Mutate the request to add a new header (e.g., "X-User-Id")
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Id", userId)
                .build();
        
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    // Helper to check for public endpoints
    private boolean isPublic(String path) {
        return publicEndpoints.stream().anyMatch(path::startsWith);
    }

    // Helper to return an error response
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        // You can set a JSON error body here if you want
        return response.setComplete();
    }
}