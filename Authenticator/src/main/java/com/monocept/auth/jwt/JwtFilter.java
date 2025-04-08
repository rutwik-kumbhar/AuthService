package com.monocept.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

public class JwtFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {


        HttpServletRequest  httpServletRequest = (HttpServletRequest)request;

        String endPoint = httpServletRequest.getServletPath();
//        log.info("doFilter : api end point {} " , endPoint);
//        if (request.getServletPath().startsWith("/api/auth/token")) {
//            chain.doFilter(request, response);
//            return;
//        }

        String header = request.getHeader("Authorization");


        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            logger.info("Token Found: " + token);

              try {
                  log.info("parse token  : {}" , token);
                  byte[] decodedKey = Base64.getDecoder().decode("kRkBIlMMJTzkMlEuty3UDc/21E6DHRraZQgr/QUIZ8s=");
                  SecretKey signingKey = Keys.hmacShaKeyFor(decodedKey);
                  Jws<Claims> claimsJws = Jwts.parserBuilder()
                          .setSigningKey(signingKey)
                          .build()
                          .parseClaimsJws(token);
                  log.info("claimsJws : {}" , claimsJws);
                  chain.doFilter(request, response);
                    return;
//                  chain.doFilter(request, response);
//                  jwtUtil.validateToken(token);
              }catch (Exception e){
                  log.info("JWT Filter : {}" , e.getMessage() );
                  chain.doFilter(request, response);
//                  response.sendError(HttpServletResponse.SC_UNAUTHORIZED , e.getMessage());
              }

        } else {
            response.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, "Invalid token Format");
            log.info("No Token Found in Request");
        }

        chain.doFilter(request, response);
    }
}
