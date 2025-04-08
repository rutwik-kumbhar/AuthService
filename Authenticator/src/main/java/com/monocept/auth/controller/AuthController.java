package com.monocept.auth.controller;

import com.monocept.auth.enums.Status;
import com.monocept.auth.model.response.MasterResponse;
import com.monocept.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
class AuthController {

    private final  UserService userService;


    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/token")
    public ResponseEntity<MasterResponse<Map<String, String>>> generateToken(@RequestBody Map<String, String> request) {
        MasterResponse<Map<String, String>> response;
        try {
            String token = userService.createUserAndGenerateToken(request);

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("token", token);

            response = new MasterResponse<>(
                    Status.SUCCESS.name(),
                    HttpStatus.OK.value(),
                    "Token Created Successfully",
                    responseBody
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error while generating token: {}", e.getMessage(), e); // optional logging

            response = new MasterResponse<>(
                    Status.FAILURE.name(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to create token",
                    null
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
