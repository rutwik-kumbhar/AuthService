package com.monocept.auth.controller;

import com.monocept.auth.jwt.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DeeplinkController {

    private final JwtUtil jwtUtil;

    public DeeplinkController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/deeplink")
    public ResponseEntity<String> generateDeeplink() {

        return  ResponseEntity.ok().body("OK");


    }
}
