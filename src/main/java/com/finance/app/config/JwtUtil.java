package com.finance.app.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    /**
     * Generates a JWT token for the given username.
     *
     * @param username the username for which the JWT token is to be generated
     * @return a JWT token as a String
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(username, claims);
    }

    /**
     * Creates a JSON Web Token (JWT) for the specified subject with the given claims.
     *
     * @param subject the subject for which the JWT token is to be created
     * @param claims  a map of claims to be included in the token
     * @return a JWT token as a String
     */
    private String createToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
