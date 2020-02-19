package com.zex.cloud.haircut.controller;

import com.zex.cloud.haircut.exception.NotSupportException;
import com.zex.cloud.haircut.params.TokenParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.response.TokenRespSimple;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.service.IOAuthService;
import com.zex.cloud.haircut.util.RedisKeys;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RequestMapping("/api/v1/oauth")
@RestController
@Api(tags = "授权操作相关接口")
public class OAuthController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IOAuthService ioAuthService;

    @PostMapping("/token")
    public TokenRespSimple token(@Valid @RequestBody TokenParam param) {

        switch (param.getGrantType()) {
            case password:
                return ioAuthService.password(param.getUsername(), param.getPassword(), param.getClientId());
            default:
                throw new NotSupportException("暂不支持该GrantType");
        }
    }


    @DeleteMapping("/token")
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