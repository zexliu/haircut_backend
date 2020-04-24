package com.zex.cloud.haircut.apis;

import com.zex.cloud.haircut.dto.QrCodeRequest;
import com.zex.cloud.haircut.response.WxAccessToken;
import com.zex.cloud.haircut.response.WxJsCodeToSessionResponse;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WxApi {

    @GET("/sns/jscode2session")
    Call<WxJsCodeToSessionResponse> jsCodeToSession(@Query("appid") String appId, @Query("secret") String secret, @Query("js_code") String jsCode, @Query("grant_type") String grantType);

    @GET("/cgi-bin/token")
    Call<WxAccessToken> getWxAccessToken(@Query("grant_type") String grantType, @Query("appid") String appId, @Query("secret") String secret);


    @POST("/wxa/getwxacodeunlimit")
    Call<ResponseBody> getQrCode(@Query("access_token")String accessToken, @Body QrCodeRequest qrCodeRequest);
}
