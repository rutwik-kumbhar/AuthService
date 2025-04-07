//package com.monocept.auth.controller;
//
//import com.monocept.auth.jwt.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/secure")
//class SecureController {
//    @Autowired
//    JwtUtil jwt;
//    @GetMapping("/data")
//    public ResponseEntity<String> securedData(@RequestHeader("Authorization") String token) {
//
//           return ResponseEntity.ok().body("accessed");
//    }
//
//    @GetMapping("/data1")
//    public ResponseEntity<String> securedData1(@RequestHeader("Authorization") String token) {
//
//        return ResponseEntity.ok().body("Bad request");
//    }
//
//}