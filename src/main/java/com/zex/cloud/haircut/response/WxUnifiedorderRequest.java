package com.zex.cloud.haircut.response;


import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * @company_name 唐山徕思歌科技有限公司
 * @auther liuze
 * @create_date 2018/6/9
 * @description 微信统一下单接口 请求参数
 */

@Data
@Root(name = "xml", strict = false)
public class WxUnifiedorderRequest {

    @Element(name = "appid")
    private String appid;

    @Element(name = "mch_id")
    private String mchId;

    @Element(name = "nonce_str")
    private String nonceStr;

    @Element(name = "sign")
    private String sign;

    @Element(name = "attach", required = false)
    private String attach;

    @Element(name = "body", required = false)
    private String body;

    @Element(name = "detail", required = false)
    private String detail;

    @Element(name = "notify_url")
    private String notifyUrl;

    @Element(name = "openid", required=false)
    private String openid;

    @Element(name = "out_trade_no")
    private String outTradeNo;

    @Element(name = "spbill_create_ip")
    private String spbillCreateIp;

    @Element(name = "total_fee")
    private Integer totalFee;

    @Element(name = "trade_type")
    private String tradeType;
    @Element(name = "time_start",required = false)
    private String timeStart;

    @Element(name = "time_expire",required = false)
    private String timeExpire;

}
