package com.zex.cloud.haircut.exception;

public class NotSupportException extends RuntimeException{

    public NotSupportException(String message) {
        super(message);
    }

    public NotSupportException() {
        super("暂不支持该操作");
    }
}
