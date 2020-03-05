package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.ClientType;
import com.zex.cloud.haircut.enums.OAuthGrantType;
import com.zex.cloud.haircut.enums.PopularizeType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TokenWxMiniParam {
    @NotNull
    private String jsCode;
    @NotNull
    private  String appId;
    @NotNull
    private ClientType clientType;

    private PopularizeType popularizeType;

    private Long popularizeId;
}
