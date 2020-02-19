package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.OAuthGrantType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TokenParam {
    private String clientId;
    private String clientSecret;
    private OAuthGrantType grantType;
    private String code;
    private String redirectUrl;
    private String username;
    private String password;
    private String refreshToken;
}
