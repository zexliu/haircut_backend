package com.zex.cloud.haircut.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @company_name 唐山徕思歌科技有限公司
 * @auther liuze
 * @create_date 2018/9/19
 * @description 微信支付校验器
 */
public class WxPaySignature {


    public static String sign(Map<String, String> params, String signKey) {
        SortedMap<String, String> sortedMap = new TreeMap<>(params);
        StringBuilder toSign = new StringBuilder();

        for (String key : sortedMap.keySet()) {
            String value = params.get(key);
            if (StringUtils.isNotEmpty(value) && !"sign".equals(key) && !"key".equals(key)) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }
        toSign.append("key=").append(signKey);

        return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
    }

    public static boolean verify(Map<String, String> params, String signKey) {
        String sign = sign(params, signKey);
        return sign.equals(params.get("sign"));
    }
}
