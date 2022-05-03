package com.eage.validation.util;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {
	
	 private final static String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
	public static String createToken() {
		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), 
		                            SignatureAlgorithm.HS256.getJcaName());
		Instant now = Instant.now();
		return Jwts.builder()
		        .claim("name", "Jane Doe")
		        .claim("email", "jane@example.com")
		        .setSubject("jane")
		        .setId(UUID.randomUUID().toString())
		        .setIssuedAt(Date.from(now))
		        .setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
		        .signWith(hmacKey)
		        .compact();
	}

	public static  Jws<Claims> validateToken(String token) {
		    Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), 
		                                    SignatureAlgorithm.HS256.getJcaName());
		    Jws<Claims> jwt = Jwts.parserBuilder()
		            .setSigningKey(hmacKey)
		            .build()
		            .parseClaimsJws(token);

		    return jwt;
	}
}
