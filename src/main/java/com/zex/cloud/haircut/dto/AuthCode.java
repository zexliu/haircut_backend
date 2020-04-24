package com.zex.cloud.haircut.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AuthCode implements Serializable {


    public AuthCode() {
    }

    public AuthCode(String code, LocalDateTime retryTime){
        this.code = code;
        this.retryTime = retryTime;
    }

    private String code;

    private LocalDateTime retryTime;




}