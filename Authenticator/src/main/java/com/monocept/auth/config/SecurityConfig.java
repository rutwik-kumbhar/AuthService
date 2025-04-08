package com.monocept.auth.config;

import com.monocept.auth.jwt.JwtFilter;
import com.monocept.auth.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final  JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain authSecurityConfig(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .cors(cors -> cors.configurationSource(request -> {

                    CorsConfiguration cfg = new CorsConfiguration();

                    cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
                    cfg.setAllowedMethods(Collections.singletonList("*"));
                    cfg.setAllowCredentials(true);
                    cfg.setAllowedHeaders(Collections.singletonList("*"));
                    cfg.setExposedHeaders(List.of("Authorization"));
                    return cfg;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtFilter(jwtUtil), BasicAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();

    }




}


//.anyRequest().authenticated()