package com.zex.cloud.haircut.util;

public class RedisKeys {

    public static String accessTokenKey(String accessToken){
        return "ACCESS_TOKEN:"+accessToken;
    }
}
