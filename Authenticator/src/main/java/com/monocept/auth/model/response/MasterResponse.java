package com.monocept.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MasterResponse<T>{
    private String status;
    private  int statusCode;
    private String message;
    private  T data;
}
