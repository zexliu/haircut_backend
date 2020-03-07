package com.zex.cloud.haircut.util;

import com.zex.cloud.haircut.enums.PermissionMethodType;

public class RedisKeys {

    public static final String WX_ACCESS_TOKEN = "WX_ACCESS_TOKEN";
    public static final String PERMISSION_ROLE = "PERMISSION_ROLE";

    public static String accessTokenKey(String accessToken){
        return "ACCESS_TOKEN:"+accessToken;
    }
    public static String hookOrderKey(String orderId){
        return "HOOK_ORDER:"+orderId;
    }

    public static String orderCodeKey(String uuid) {
        return "ORDER_CODE:"+uuid;
    }

    public static String permissionKey(String permissionUrl , PermissionMethodType methodType){
        return permissionUrl + "#" + methodType.name();
    }
}
