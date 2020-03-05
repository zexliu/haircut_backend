package com.zex.cloud.haircut.util;

public class RedisKeys {

    public static final String WX_ACCESS_TOKEN = "WX_ACCESS_TOKEN";

    public static String accessTokenKey(String accessToken){
        return "ACCESS_TOKEN:"+accessToken;
    }
    public static String hookOrderKey(String orderId){
        return "HOOK_ORDER:"+orderId;
    }

    public static String orderCodeKey(String uuid) {
        return "ORDER_CODE:"+uuid;
    }
}
