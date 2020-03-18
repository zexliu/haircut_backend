package com.zex.cloud.haircut.params;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel("用户注册请求参数")
public class SyUserRegisterParam {

    @ApiModelProperty("用户名")
    @NotBlank
    @Length( min = 5,max = 20,message = "用户名长度在 5 - 20 位之间")
    private String username;
    @ApiModelProperty("密码 , MD5加密后的用户密码")
    @NotBlank
    private String password;

}
