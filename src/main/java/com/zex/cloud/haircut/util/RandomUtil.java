package com.zex.cloud.haircut.util;

import org.apache.commons.lang3.RandomUtils;

/**
 * @company_name 唐山徕思歌科技有限公司
 * @auther liuze
 * @create_date 2018/9/19
 * @description 随机数工具类
 */
public class RandomUtil {


    private static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


    public static String getRandomStr() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(RANDOM_STR.charAt(RandomUtils.nextInt(0, RANDOM_STR.length() - 1)));
        }
        return sb.toString();
    }

}
