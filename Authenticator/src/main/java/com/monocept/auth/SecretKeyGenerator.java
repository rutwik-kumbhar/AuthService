//package com.monocept.auth;
//
//import java.util.Base64;
//import javax.crypto.SecretKey;
//import io.jsonwebtoken.security.Keys;
//
//public class SecretKeyGenerator {
//    public static void main(String[] args) {
//        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
//        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
//        System.out.println("Base64 Encoded Secret Key: " + encodedKey);
//    }
//}
