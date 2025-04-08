package com.monocept.auth.service.impl;

import com.monocept.auth.entity.User;
import com.monocept.auth.jwt.JwtUtil;
import com.monocept.auth.repository.UserRepository;
import com.monocept.auth.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService , UserDetailsService {




    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil, RedisTemplate<String, Object> redisTemplate) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String createUserAndGenerateToken(Map<String, String> request) {

        String redisKey = "user:request:" + request.get("");

        // 1. Check if data already exists in Redis
        Boolean exists = redisTemplate.hasKey(redisKey);

        // 2. If not exists, store the request data
        if (Boolean.FALSE.equals(exists)) {
            redisTemplate.opsForValue().set(redisKey, request); // store without TTL
        }

        Optional<User> optionalUser = userRepository.findByAgentId(request.get("agentId"));

        if (optionalUser.isEmpty()) {
            User  user = User.builder()
                    .agentId(request.get("agentId"))
                    .firstName(request.get("firstName"))
                    .lastName(request.get("lastName"))
                    .email(request.get("email"))
                    .role(request.get("role"))
                    .firebaseId(request.get("firebaseId"))
                    .deviceId(request.get("deviceId"))
                    .createdAt(ZonedDateTime.now())
                    .updatedAt(ZonedDateTime.now())
                    .build();
            userRepository.save(user);
        }
        return jwtUtil.generateToken(request.get("email"), request); // assuming you use email + user for token
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
