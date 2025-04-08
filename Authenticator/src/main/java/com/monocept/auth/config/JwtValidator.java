//package com.monocept.auth.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.crypto.SecretKey;
//import java.io.IOException;
//import java.util.List;
//
//public class JwtValidator extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        // TODO Auto-generated method stub
//
//        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
//        System.out.println(jwt);
//
//
//        if(jwt != null) {
//            jwt = jwt.substring(7);
//
//
//            try {
//                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
//
//                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
//                String email = String.valueOf(claims.get("email"));
//
//                String autorities = String.valueOf(claims.get("autorities"));
//
//                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(autorities);
//
//                Authentication authentication = new UsernamePasswordAuthenticationToken(email,null,auths);
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } catch (Exception e) {
//                // TODO: handle exception
//                throw new BadCredentialsException("Invalid tokan...! From jwt validator");
//            }
//        }
//
//        filterChain.doFilter(request, response);
//
//    }
//
//
//
//}
