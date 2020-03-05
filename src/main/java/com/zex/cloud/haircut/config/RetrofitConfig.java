package com.zex.cloud.haircut.config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.util.Arrays;

@Configuration
public class RetrofitConfig {

    @Bean
    public Retrofit wxPayRetrofit() throws Exception {
        //配置微信证书
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("wx_cert.p12");
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        char[] password = WxProperties.PARTNER_ID.toCharArray();
        keystore.load(inputStream, password);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keystore, password);
        KeyManager[] kms = kmf.getKeyManagers();
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keystore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
        sslContext.init(kms, new TrustManager[]{trustManager}, new SecureRandom());
        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        return new Retrofit.Builder()
                .baseUrl(WxProperties.WX_PAY_GATEWAY)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor((new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY)))
                        .sslSocketFactory(socketFactory, trustManager)
                        .build()
                ).build();
    }

    @Bean
    public Retrofit wxRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(WxProperties.WX_GATEWAY)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor((new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY)))
                        .build()
                ).build();
    }

}
