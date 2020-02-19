package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.response.TokenRespSimple;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.service.IOAuthService;
import com.zex.cloud.haircut.service.ISyUserService;
import com.zex.cloud.haircut.util.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class OAuthServiceImpl implements IOAuthService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ISyUserService iSyUserService;
    @Override
    public TokenRespSimple password(String username, String password, String clientId) {
        RequestUser requestUser = iSyUserService.getRequestUser(username,password);
        String accessToken = UUID.randomUUID().toString();
        TokenRespSimple tokenRespSimple = new TokenRespSimple();
        tokenRespSimple.setAccessToken(accessToken);
        redisTemplate.opsForValue().set(RedisKeys.accessTokenKey(accessToken),requestUser,30, TimeUnit.DAYS);
        return tokenRespSimple;
    }

    @Override
    public void cancelAuth(String accessToken) {
        redisTemplate.delete(RedisKeys.accessTokenKey(accessToken));
    }

}
