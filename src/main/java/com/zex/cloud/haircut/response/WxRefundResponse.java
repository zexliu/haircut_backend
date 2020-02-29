package com.zex.cloud.haircut.response;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @company_name 唐山徕思歌科技有限公司
 * @auther liuze
 * @create_date 2018/9/19
 * @description 微信退款返回
 */

@Root(name = "xml", strict = false)
@Data
public class WxRefundResponse {

    @Element(name = "return_code", required = false)
    private String returnCode;

    @Element(name = "return_msg", required = false)
    private String returnMsg;

    /** 以下字段在return_code为SUCCESS的时候有返回. */
    @Element(name = "appid", required = false)
    private String appid;

    @Element(name = "mch_id", required = false)
    private String mchId;

    @Element(name = "nonce_str", required = false)
    private String nonceStr;

    @Element(name = "sign", required = false)
    private String sign;

    @Element(name = "result_code", required = false)
    private String resultCode;

    @Element(name = "err_code", required = false)
    private String errCode;

    @Element(name = "err_code_des", required = false)
    private String errCodeDes;

    @Element(name = "transaction_id", required = false)
    private String transactionId;

    @Element(name = "out_trade_no", required = false)
    private String outTradeNo;

    @Element(name = "out_refund_no", required = false)
    private String outRefundNo;

    @Element(name = "refund_id", required = false)
    private String refundId;

    @Element(name = "refund_channel", required = false)
    private String refundChannel;

    @Element(name = "refund_fee", required = false)
    private String refundFee;
    @Element(name = "req_info", required = false)
    private String reqInfo;

}
