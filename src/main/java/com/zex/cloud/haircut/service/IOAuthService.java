package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.enums.ClientType;
import com.zex.cloud.haircut.params.TokenWxMiniParam;
import com.zex.cloud.haircut.response.TokenRespSimple;

import java.io.IOException;

public interface IOAuthService {
    TokenRespSimple password(String username, String password, ClientType clientType);


    void cancelAuth(String accessToken);

    TokenRespSimple wxMiniToken(TokenWxMiniParam param);


    String getBackendAccessToken() ;
}
