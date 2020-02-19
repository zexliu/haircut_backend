package com.zex.cloud.haircut.security;

public class RequestHolder {

    private static final ThreadLocal<RequestUser> userHolder = new InheritableThreadLocal<>();

    public static RequestUser user(){
        return userHolder.get();
    }

    public static void remove(){
        userHolder.remove();
    }

    public static void put(RequestUser redisUser){
        userHolder.set(redisUser);
    }

}
