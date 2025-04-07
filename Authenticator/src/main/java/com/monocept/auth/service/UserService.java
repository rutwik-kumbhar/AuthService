package com.monocept.auth.service;

import java.util.Map;

public interface UserService {
   String createUserAndGenerateToken(Map<String, String> request);
}
