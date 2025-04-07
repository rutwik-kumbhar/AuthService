package com.monocept.auth.model.request;


import lombok.Data;

@Data
public class UserRequest {
    private String agentId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String firebaseId;
    private String deviceId;
}
