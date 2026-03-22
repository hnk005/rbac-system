package org.springboot.rbacsystem.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springboot.rbacsystem.properties.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@EnableConfigurationProperties
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {
	
	private final JwtProperties properties;
	
	public String generateJwtToken(Authentication authentication) {
		
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		
		if (userPrincipal == null) {
			return null;
		}
		
		return Jwts.builder()
		           .setSubject((userPrincipal.getUsername()))
		           .setIssuedAt(new Date())
		           .setExpiration(new Date((new Date()).getTime() + properties.getExpirationTime()))
		           .signWith(key(), SignatureAlgorithm.HS256)
		           .compact();
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.getSecretKey()));
	}
	
	public String getUserNameFromJwtToken(String token) throws JwtException {
		return Jwts.parserBuilder()
		           .setSigningKey(key())
		           .build()
		           .parseClaimsJws(token)
		           .getBody()
		           .getSubject();
	}
}