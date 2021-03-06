package com.zex.cloud.haircut.response;

import lombok.Data;
import org.simpleframework.xml.Element;

/**
 * @company_name 唐山徕思歌科技有限公司
 * @auther liuze
 * @create_date 2018/9/19
 * @description 微信返回App
 */
@Data
public class WxAppResponse {
    String appid;
    String partnerid;
    String prepayid;

    @Element(name = "package")
    String packageValue;

    String noncestr;
    String timestamp;
    String sign;

}
