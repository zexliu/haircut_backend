package com.zex.cloud.haircut.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zex.cloud.haircut.dto.AuthCode;
import com.zex.cloud.haircut.enums.AuthCodeType;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.service.ISmsService;
import com.zex.cloud.haircut.util.RedisKeys;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements ISmsService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "sms11.hzgxr.com:40081";



    static final String accessKeyId = "XNY2_IU4=A7VJGFm4yl0=sIwOEg==";
    static final String accessKeySecret = "IItE8bL7LLE=nV60jNGir6U=";


    @Override
    public void sendAuthCode(String mobile, AuthCodeType authCodeType, Integer expireMinute) {

        //过期时间 默认30分钟
        if (expireMinute == null || expireMinute <= 0) {
            expireMinute = 30;
        }
        AuthCode authCode = (AuthCode) redisTemplate.opsForValue().get(RedisKeys.authCode(mobile,authCodeType));
        if (authCode != null && authCode.getRetryTime().isAfter(LocalDateTime.now())) {
            throw new ForbiddenException("验证码发送间隔过短");
        }

        //生成验证码
        String authCodeStr = RandomStringUtils.randomNumeric(6);

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setMethod(MethodType.POST);
        request.setAcceptFormat(FormatType.JSON);
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("亿店圈");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(authCodeType.getTemplateCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{ \"code\":\""+authCodeStr+"\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("yourOutId");
        //hint 此处可能会抛出异常，注意catch
        try {
            SendSmsResponse acsResponse = acsClient.getAcsResponse(request);
            if (acsResponse.getMessage().equals("OK")){
                authCode = new AuthCode(authCodeStr, LocalDateTime.now().plusMinutes(1));
                redisTemplate.opsForValue().set(RedisKeys.authCode(mobile,authCodeType),authCode,expireMinute,TimeUnit.MINUTES);
            }else {
                throw new ServerException(acsResponse.getMessage());
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAuthCodePass(String mobile, AuthCodeType authCodeType, String authCodeStr) {
        AuthCode authCode = (AuthCode) redisTemplate.opsForValue().get(RedisKeys.authCode(mobile,authCodeType));
        if (authCode != null && StringUtils.equals(authCodeStr, authCode.getCode())) {
            //验证成功,删除验证码
            redisTemplate.delete(RedisKeys.authCode(mobile,authCodeType));
            return true;
        } else {
            // TODO: 2018/11/29 写死的验证码
            return authCodeStr.equals("851027");
        }
    }
}
