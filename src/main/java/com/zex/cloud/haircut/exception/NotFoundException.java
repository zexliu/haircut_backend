package com.zex.cloud.haircut.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        super("资源♀不存在");
    }
}
