package com.zex.cloud.haircut.params;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户注册请求参数")
public class SyUserRegisterParam {

    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码 , MD5加密后的用户密码")
    private String password;

}
