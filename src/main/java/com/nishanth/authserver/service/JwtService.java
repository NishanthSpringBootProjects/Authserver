package com.nishanth.authserver.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private String secretKey = "";

	public JwtService() throws NoSuchAlgorithmException {
		/* this will automatically generate the random secret key everytime */
		KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
		SecretKey sk = keyGen.generateKey();
		secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());

	}

	/*
	 * used to generate a JWT token with subject as username signWith method in JWT
	 * (JSON Web Token) is used to digitally sign the token to ensure its integrity
	 * and authenticity.
	 */

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(userName)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30)).and()
				.signWith(getKey())
				.compact();

	}

	/*
	 * method to generate key
	 */
	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUserNameFromToken(String token) {
		
		return extractClaim(token,Claims::getSubject);
		
	}

	private <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	

	private Claims extractAllClaims(String token) {
//		return Jwts.parser().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
		return Jwts.parser()
				.verifyWith(getKey())
				.build().
				parseSignedClaims(token)
				.getPayload();
		
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && ! isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		
		 Boolean isExpired = extractExpiration(token).before(new Date());
		 return isExpired;
	}

	private Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}

}
