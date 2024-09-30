package com.finance.app.financeapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Generates a JWT token for the given username.
     *
     * @param username the username for which the token is to be generated
     * @return the generated JWT token as a String
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token the JWT token from which to extract the username
     * @return the username contained within the JWT token
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /**
     * Validates the given JWT token against the provided username. The token is considered valid if the username extracted from the token matches
     * the provided username and the token is not expired.
     *
     * @param token the JWT token to be validated
     * @param username the username to validate against the token
     * @return true if the token is valid and the username matches; false otherwise
     */
    public boolean validateToken(String token, String username) {
        String tokenUsername = getUsernameFromToken(token);
        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }

    /**
     * Checks if the provided JWT token has expired.
     *
     * @param token the JWT token to check
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }
}
