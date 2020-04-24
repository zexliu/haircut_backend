package com.zex.cloud.haircut.service;


import com.zex.cloud.haircut.enums.AuthCodeType;

public interface ISmsService {


     void sendAuthCode(String mobile, AuthCodeType authCodeType, Integer expireMinute);

     boolean isAuthCodePass(String mobile, AuthCodeType authCodeType, String authCodeStr);


}
