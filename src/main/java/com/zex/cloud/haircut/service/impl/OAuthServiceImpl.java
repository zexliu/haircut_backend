package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.apis.WxApi;
import com.zex.cloud.haircut.config.WxProperties;
import com.zex.cloud.haircut.enums.ClientType;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.params.TokenWxMiniParam;
import com.zex.cloud.haircut.response.TokenRespSimple;
import com.zex.cloud.haircut.response.WxAccessToken;
import com.zex.cloud.haircut.response.WxJsCodeToSessionResponse;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.service.IOAuthService;
import com.zex.cloud.haircut.service.ISmShopService;
import com.zex.cloud.haircut.service.ISyUserService;
import com.zex.cloud.haircut.util.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class OAuthServiceImpl implements IOAuthService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ISyUserService iSyUserService;
    @Autowired
    private ISmShopService iSmShopService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Retrofit wxRetrofit;

    @Override
    public TokenRespSimple password(String username, String password, ClientType clientType) {
        RequestUser requestUser = iSyUserService.getRequestUser(username, password, clientType);
        return saveToRedis(requestUser);
    }

    @Override
    public void cancelAuth(String accessToken) {
        redisTemplate.delete(RedisKeys.accessTokenKey(accessToken));
    }

    @Override
    public TokenRespSimple wxMiniToken(TokenWxMiniParam param)  {
        Call<WxJsCodeToSessionResponse> call = wxRetrofit.create(WxApi.class).jsCodeToSession(param.getAppId(), WxProperties.APPS.get(param.getAppId()), param.getJsCode(), "authorization_code");
        try {
            Response<WxJsCodeToSessionResponse> execute =  call.execute();
            WxJsCodeToSessionResponse body = execute.body();
            if (execute.isSuccessful() && body != null) {
                if (body.getErrCode() == 0) {
                    RequestUser requestUser = iSyUserService.getRequestUser(body.getUnionId(), body.getOpenId(), body.getSessionKey(), param.getAppId(), param.getClientType(),param.getPopularizeId(),param.getPopularizeType());
                    return saveToRedis(requestUser);
                } else {
                    throw new ServerException(body.getErrMsg());
                }
            } else {
                throw new ServerException("调用微信网络异常");
            }
        } catch (IOException e) {
            throw new ServerException("调用微信IO异常");
        }

    }

    @Override
    public String getBackendAccessToken() {
        String accessToken = stringRedisTemplate.opsForValue().get(RedisKeys.WX_ACCESS_TOKEN);
        if (accessToken == null) {
            Call<WxAccessToken> call = wxRetrofit.create(WxApi.class).getWxAccessToken("client_credential", WxProperties.APP_ID, WxProperties.APPS.get(WxProperties.APP_ID));
            try {
                Response<WxAccessToken> execute = call.execute();
                WxAccessToken body = execute.body();
                if (execute.isSuccessful() && body != null) {
                    if (body.getErrCode() == 0) {
                        accessToken = body.getAccessToken();
                        stringRedisTemplate.opsForValue().set(RedisKeys.WX_ACCESS_TOKEN, accessToken, body.getExpiresIn(), TimeUnit.SECONDS);
                    } else {
                        throw new ServerException(body.getErrMsg());
                    }
                } else {
                    throw new ServerException("调用微信网络异常");
                }
            } catch (IOException e) {
                throw new ServerException("调用微信IO异常");
            }
        }
        return accessToken;
    }

    private TokenRespSimple saveToRedis(RequestUser requestUser) {
        String accessToken = UUID.randomUUID().toString();
        TokenRespSimple tokenRespSimple = new TokenRespSimple();
        tokenRespSimple.setAccessToken(accessToken);
        redisTemplate.opsForValue().set(RedisKeys.accessTokenKey(accessToken), requestUser, 30, TimeUnit.DAYS);
        return tokenRespSimple;
    }

}
