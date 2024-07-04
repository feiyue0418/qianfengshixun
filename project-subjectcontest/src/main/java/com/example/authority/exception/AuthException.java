package com.example.authority.exception;

import lombok.Data;

@Data
public class AuthException extends RuntimeException{

    private String code;

    public AuthException( String code,String msg) {
        super(msg);
        this.code = code;
    }
}
