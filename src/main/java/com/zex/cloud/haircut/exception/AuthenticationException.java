package com.zex.cloud.haircut.exception;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException() {
        super("认证失败");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
