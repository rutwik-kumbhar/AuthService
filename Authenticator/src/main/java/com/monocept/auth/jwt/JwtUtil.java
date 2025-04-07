package com.monocept.auth.jwt;

import com.monocept.auth.config.TokenConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
@Slf4j
@Configuration
@Data
public class JwtUtil {


    @Value("${jwt.secret}")
    private String jwtSecret;



    /**
     * Generates a JWT token using the username and custom claims.
     * @param username the username
     * @param claims   additional payload
     * @return signed JWT token
     */
    public String generateToken(String username, Map<String, String> claims) {
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
        SecretKey signingKey = Keys.hmacShaKeyFor(decodedKey);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(TokenConfig.EXPIRATION_TIME.toInstant()))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }


//    public Claims extractClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(key)
//                .parseClaimsJws(token)
//                .getBody();
//    }

    /**
     * Extracts all claims from the JWT token.
     * @param token the JWT token
     * @return the Claims object
     */
    public Claims extractClaims(String token) {
        return parseToken(token).getBody();
    }

    /**
     * Extracts the username (subject) from the token.
     * @param token the JWT token
     * @return the subject/username
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Parses the JWT token using the secret key.
     * @param token the JWT token
     * @return Jws<Claims> object
     */
    private Jws<Claims> parseToken(String token) {
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
        SecretKey signingKey = Keys.hmacShaKeyFor(decodedKey);
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token);
    }


    /**
     * Validates the token by checking signature and expiration.
     * Throws appropriate exceptions if invalid.
     * @param token the JWT token
     */
    public void validateToken(String token) {
        try {
            parseToken(token);
        } catch (ExpiredJwtException e) {
            log.warn("JWT token expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.warn("JWT token unsupported: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.warn("JWT token malformed: {}", e.getMessage());
            throw e;
        } catch (SignatureException e) {
            log.warn("JWT signature invalid: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.warn("JWT validation error: {}", e.getMessage());
            throw new RuntimeException("JWT validation failed", e);
        }
    }



}
