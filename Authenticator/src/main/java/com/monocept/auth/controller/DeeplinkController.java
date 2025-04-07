//package com.monocept.auth.controller;
//
//import com.monocept.auth.jwt.JwtUtil;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//public class DeeplinkController {
//
//    private final JwtUtil jwtUtil;
//
//    public DeeplinkController(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @GetMapping("/deeplink")
//    public ResponseEntity<?> generateDeeplink(@RequestParam String token) {
//        // Validate the JWT token
//        if (!jwtUtil.isTokenValid(token)) {
//            return ResponseEntity.status(401).body("Invalid or expired token");
//        }
//
//        // Generate deep link
//        String deepLink = "https://mspace.example.com/chatbot-ui?token=" + token;
//
//        return ResponseEntity.ok().body("{\"deeplink\": \"" + deepLink + "\"}");
//    }
//}
