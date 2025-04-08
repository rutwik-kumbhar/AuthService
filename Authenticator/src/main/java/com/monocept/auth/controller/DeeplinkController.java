package com.monocept.auth.controller;

import com.monocept.auth.enums.Status;
import com.monocept.auth.jwt.JwtUtil;
import com.monocept.auth.model.response.MasterResponse;
import com.monocept.auth.service.DeeplinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class DeeplinkController {


    private final DeeplinkService deeplinkService;

    public DeeplinkController(DeeplinkService deeplinkService) {
        this.deeplinkService = deeplinkService;
    }

    @GetMapping("/deeplink")
    public ResponseEntity<MasterResponse<Map<String, String>>> generateDeeplink(@RequestHeader("Authorization") String token) {
        MasterResponse<Map<String, String>> response;

        try {
            String deeplinkUrl = deeplinkService.createDeeplinkUrl(token);
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("deeplinkUrl", deeplinkUrl);
            response = new MasterResponse<>(Status.SUCCESS.name(), HttpStatus.OK.value(), "Deeplink generated successfully", responseBody);
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            log.error("Error while generating deeplink: {}", exception.getMessage(), exception);
            response = new MasterResponse<>(Status.FAILURE.name(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to generate deeplink", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
