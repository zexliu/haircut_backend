package com.zex.cloud.haircut.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;

public class AESUtil {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING_5 = "AES/ECB/PKCS5Padding";
    private static final String ALGORITHM_MODE_PADDING_7 = "AES/CBC/PKCS7Padding";

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String data,String signKey) throws Exception {
        // 创建密码器  
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING_5);

        //生成key
        SecretKeySpec key = new SecretKeySpec(DigestUtils.md5Hex(signKey).toLowerCase().getBytes(), ALGORITHM);

        // 初始化  
        cipher.init(Cipher.ENCRYPT_MODE, key);


        return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
    }

    public static String encryptData(String data,String signKey,String iv) throws Exception {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING_7);

        //生成key
        SecretKeySpec key = new SecretKeySpec(Base64.decodeBase64(signKey), ALGORITHM);
        AlgorithmParameters params = AlgorithmParameters.getInstance(ALGORITHM);
        params.init(new IvParameterSpec(iv.getBytes()));

        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, key,params);


        return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
    }


    /**
     * AES解密
     *
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static String decryptData(String base64Data,String signKey,String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING_7);
        SecretKeySpec key = new SecretKeySpec(Base64.decodeBase64(signKey), ALGORITHM);
        AlgorithmParameters params = AlgorithmParameters.getInstance(ALGORITHM);
        params.init(new IvParameterSpec(Base64.decodeBase64(iv)));
        // 初始化
        cipher.init(Cipher.DECRYPT_MODE, key,params);
        return new String(cipher.doFinal(Base64.decodeBase64(base64Data)));
    }


    /**
     * AES解密
     *
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static String decryptData(String base64Data,String signKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING_5);
        SecretKeySpec key = new SecretKeySpec(DigestUtils.md5Hex(signKey).toLowerCase().getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.decodeBase64(base64Data)));
    }


}