package com.monocept.auth.service.impl;

import com.monocept.auth.entity.User;
import com.monocept.auth.jwt.JwtUtil;
import com.monocept.auth.repository.UserRepository;
import com.monocept.auth.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService , UserDetailsService {


    private final  UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String createUserAndGenerateToken(Map<String, String> request) {
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
