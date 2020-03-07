package com.zex.cloud.haircut.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class WxUserInfoParam {


    /**
     * openId : oGZUI0egBJY1zhBYw2KhdUfwVJJE
     * nickName : Band
     * gender : 1
     * language : zh_CN
     * city : Guangzhou
     * province : Guangdong
     * country : CN
     * avatarUrl : http://wx.qlogo.cn/mmopen/vi_32/aSKcBBPpibyKNicHNTMM0qJVh8Kjgiak2AHWr8MHM4WgMEm7GFhsf8OYrySdbvAMvTsw3mo8ibKicsnfN5pRjl1p8HQ/0
     * unionId : ocMvos6NjeKLIBqg5Mr9QjxrP1FA
     * watermark : {"timestamp":1477314187,"appid":"wx4f4bc4dec97d474b"}
     */

    private String openId;
    private String nickName;
    private Integer gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;
    private WatermarkBean watermark;

    @Data
    public static class WatermarkBean {
        /**
         * timestamp : 1477314187
         * appid : wx4f4bc4dec97d474b
         */

        private int timestamp;
        private String appid;
    }
}
