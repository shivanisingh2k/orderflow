package com.orderflow.orderflow.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secret;

	private SecretKey secretKey;

	@PostConstruct
	public void init() {
	    secretKey = Keys.hmacShaKeyFor(secret.getBytes());
	}

    public String generateToken(String email, String role) {

    	return Jwts.builder()
    	        .subject(email)
    	        .claim("role", role)
    	        .issuedAt(new Date())
    	        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
    	        .signWith(secretKey)
    	        .compact();
    }
    public String extractEmail(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public boolean isTokenValid(String token, String email) {

        String tokenEmail = extractEmail(token);

        return tokenEmail.equals(email);
    }
    public String extractRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
}