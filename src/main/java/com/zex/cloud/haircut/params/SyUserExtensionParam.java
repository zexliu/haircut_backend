package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.GenderType;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

//用户拓展信息
@Data
public class SyUserExtensionParam {

    @ApiModelProperty("性别")
    private GenderType gender;

    @ApiModelProperty("生日")
    private LocalDate birthDay;
    @ApiModelProperty("职业")
    private String job;
    @ApiModelProperty("学历")
    private String education;
    @ApiModelProperty("脸型")
    private String face;
    @ApiModelProperty("发长")
    private String hairHeight;
    @ApiModelProperty("发质")
    private String hairQuality;
    @ApiModelProperty("发型")
    private String hairStyle;
    @ApiModelProperty("偶像")
    private String idol;

}
