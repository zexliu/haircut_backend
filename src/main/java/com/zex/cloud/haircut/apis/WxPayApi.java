package com.zex.cloud.haircut.apis;

import com.zex.cloud.haircut.response.WxCreateOrderResponse;
import com.zex.cloud.haircut.response.WxOrderQueryResponse;
import com.zex.cloud.haircut.response.WxRefundResponse;
import com.zex.cloud.haircut.response.WxTransferResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @company_name 唐山徕思歌科技有限公司
 * @auther liuze
 * @create_date 2018/9/19
 * @description 微信Api
 */
public interface WxPayApi {



    /**
     * 统一下单
     *
     * @param body
     * @return https://api.mch.weixin.qq.com
     */
    @POST("/pay/unifiedorder")
    Call<WxCreateOrderResponse> unifiedorder(@Body RequestBody body);

    /**
     * 订单查询
     *
     * @param body
     * @return https://api.mch.weixin.qq.com
     */
    @POST("/pay/orderquery")
    Call<WxOrderQueryResponse> orderquery(@Body RequestBody body);


    /**
     * 转账给用户
     * @param body
     * @return https://api.mch.weixin.qq.com
     */
    @POST("/mmpaymkttransfers/promotion/transfers")
    Call<WxTransferResponse> transfer(@Body RequestBody body);  /**


     * 申请退款
     * @param body
     * @return https://api.mch.weixin.qq.com
     */
    @POST("/secapi/pay/refund")
    Call<WxRefundResponse> refund(@Body RequestBody body);
}
