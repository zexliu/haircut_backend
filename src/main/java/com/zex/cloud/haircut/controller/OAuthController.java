package com.zex.cloud.haircut.controller;

import com.zex.cloud.haircut.enums.OAuthGrantType;
import com.zex.cloud.haircut.exception.NotSupportException;
import com.zex.cloud.haircut.params.TokenParam;
import com.zex.cloud.haircut.params.TokenWxMiniParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.response.TokenRespSimple;
import com.zex.cloud.haircut.service.IOAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;


@RequestMapping("/api/v1/oauth")
@RestController
@Api(tags = "授权操作相关接口")
public class OAuthController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IOAuthService ioAuthService;

    @PostMapping("/token")
    @ApiOperation("登录")
    public TokenRespSimple token(@Valid @RequestBody TokenParam param) {
        if (param.getGrantType() == OAuthGrantType.password) {
            return ioAuthService.password(param.getUsername(), param.getPassword(), param.getClientType());
        }
        throw new NotSupportException("暂不支持该GrantType");
    }

    @PostMapping("/token/wx/mini")
    @ApiOperation("微信小程序登录")
    public TokenRespSimple wxMiniToken(@RequestBody @Valid TokenWxMiniParam param) {
        return ioAuthService.wxMiniToken(param);
    }

    @DeleteMapping("/token")
    @ApiOperation("退出登录")
    public String token() {
        String authenticationHeader = request.getHeader("Authorization");
        String accessToken = "";
        if (StringUtils.isNotBlank(authenticationHeader) && StringUtils.startsWith(authenticationHeader,"Bearer ")){
            accessToken = authenticationHeader.substring(7);
        }
        ioAuthService.cancelAuth(accessToken);
        return SimpleResp.SUCCESS;
    }

}
