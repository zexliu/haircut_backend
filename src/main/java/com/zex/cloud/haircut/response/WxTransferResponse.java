package com.zex.cloud.haircut.response;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @company_name 唐山徕思歌科技有限公司
 * @auther liuze
 * @create_date 2018/9/17
 * @description 微信转账返回
 */

@Root(name = "xml", strict = false)
@Data
public class WxTransferResponse {

    @Element(name = "return_code")
    private String returnCode;

    @Element(name = "return_msg", required = false)
    private String returnMsg;

    /** 以下字段在return_code为SUCCESS的时候有返回. */
    @Element(name = "appid", required = false)
    private String appid;

    @Element(name = "mch_id", required = false)
    private String mchId;

    @Element(name = "device_info", required = false)
    private String deviceInfo;

    @Element(name = "nonce_str", required = false)
    private String nonceStr;

    @Element(name = "result_code", required = false)
    private String resultCode;


    @Element(name = "partner_trade_no", required = false)
    private String partnerTradeNo;


    @Element(name = "payment_no", required = false)
    private String paymentNo;

    @Element(name = "payment_time", required = false)
    private String paymentTime;
    @Element(name = "err_code", required = false)
    private String errCode;

    @Element(name = "err_code_des", required = false)
    private String errCodeDes;
}
