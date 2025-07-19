package com.sample.Work.security.jwt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sample.Work.model.TokenEntity;
import com.sample.Work.repository.TokenRepository;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {
	
	@Autowired
	private TokenRepository tokenRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${spring.app.jwt.expirationMs}")
	private int jwtExpirationMs;

	@Value("${spring.app.jwt.secret}")
	private String jwtSecret;
	
	public String getjwtFromHeader(HttpServletRequest request) {
		// Step 1 get the bearer token from the request header
		String bearerToken = request.getHeader("Authorization");
		// Step 2 - log the value of the bearer token
		logger.debug("Authorization Header : {} ",bearerToken);
		// Step 3 - now check whether the token is valid or not 
		if(bearerToken!=null && bearerToken.startsWith("Bearer ")){
			return bearerToken.substring(7);
		}
		
		return null;
	}
	// Generating Token from Username
	
	public String generateTokenFromUsername( UserDetails userDetails){
		String username = userDetails.getUsername();
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(new Date(new Date().getTime() + jwtExpirationMs))
				.signWith(key())
				.compact();
	}
	// Getting Username from JWT Token
	
	public String getUserNameFromJWTToken(String token) {
		return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token)
				.getPayload().getSubject();
	}
	// Generate Signing Key
	
	public Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	// Validate JWT Token
	
	public boolean validateJwtToken(String authToken) {
		try {
			TokenEntity token = tokenRepository.findByToken(authToken).orElseThrow(() -> new RuntimeException("Invalid token"));
			if(token.isLoggedOut()) {
				logger.warn("Token has been logged out");
				return false;
			}
			System.out.println("Validate");
			Jwts.parser().
			verifyWith((SecretKey) key())
			.build()
			.parseSignedClaims(authToken);
		return true;
		}
		catch(MalformedJwtException e){
			logger.error("Invalid JWT token: {}" , e.getMessage());
		}
		catch(ExpiredJwtException e) {
			logger.error("JWT token is expired: {}" , e.getMessage());			
		}catch(UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		}catch(IllegalArgumentException e){
			logger.error("JWT claims string is empty: {}" , e.getMessage());
		}
		
		return false;
		
	}
}
