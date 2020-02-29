package com.zex.cloud.haircut.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.simpleframework.xml.Element;

/**
 * @company_name 唐山徕思歌科技有限公司
 * @auther liuze
 * @create_date 2018/9/19
 * @description 微信返回App
 */
@Data
public class WxJsResponse {
    String appId;

    @Element(name = "package")
    @JsonProperty("package")
    String packageValue;

    String nonceStr;

    String timeStamp;
    String signType;
    String paySign;
}
