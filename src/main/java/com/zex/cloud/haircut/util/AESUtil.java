package com.zex.cloud.haircut.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String data,String signKey) throws Exception {
        // 创建密码器  
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);

        //生成key
        SecretKeySpec key = new SecretKeySpec(DigestUtils.md5Hex(signKey).toLowerCase().getBytes(), ALGORITHM);

        // 初始化  
        cipher.init(Cipher.ENCRYPT_MODE, key);


        return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密
     *
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static String decryptData(String base64Data,String signKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        SecretKeySpec key = new SecretKeySpec(DigestUtils.md5Hex(signKey).toLowerCase().getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.decodeBase64(base64Data)));
    }


}