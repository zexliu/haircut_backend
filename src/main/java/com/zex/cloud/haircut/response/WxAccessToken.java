package com.zex.cloud.haircut.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WxAccessToken {


    @JsonProperty("access_token")
   private String accessToken ;

    @JsonProperty("expires_in")
    private Integer expiresIn ;

    @JsonProperty("errcode")
    private Integer errCode;

    @JsonProperty("errmsg")
    private String errMsg;
}
