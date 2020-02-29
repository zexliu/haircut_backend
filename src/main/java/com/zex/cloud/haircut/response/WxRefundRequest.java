package com.zex.cloud.haircut.response;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @company_name 唐山徕思歌科技有限公司
 * @auther liuze
 * @create_date 2018/9/19
 * @description TODO
 */


@Data
@Root(name = "xml", strict = false)
public class WxRefundRequest {
    @Element(name = "appid")
    private String appid;
    @Element(name = "mch_id")
    private String mchId;

    @Element(name = "nonce_str")
    private String nonceStr;

    @Element(name = "out_refund_no")
    private String outRefundNo;

    @Element(name = "out_trade_no")
    private String outTradeNo;

    @Element(name = "refund_fee")
    private String refundFee;

    @Element(name = "total_fee")
    private String totalFee;

    @Element(name = "transaction_id",required = false)
    private String transactionId;
    @Element(name = "sign")
    private String sign;

    @Element(name = "notify_url")
    private String notifyUrl;

    @Element(name = "refund_desc")
    private String refundDesc;


}
