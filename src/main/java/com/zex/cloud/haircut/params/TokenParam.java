package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.ClientType;
import com.zex.cloud.haircut.enums.OAuthGrantType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TokenParam {
    //    private String clientId;
//    private String clientSecret;
    @NotNull
    private OAuthGrantType grantType;
    //    private String code;
//    private String redirectUrl;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
//    private String refreshToken;
    @NotNull
    private ClientType clientType;
}
