package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.response.TokenRespSimple;

public interface IOAuthService {
    TokenRespSimple password(String username, String password, String clientId);


    void cancelAuth(String accessToken);
    
}
