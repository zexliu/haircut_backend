package com.zex.cloud.haircut.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SmAgentApply请求对象", description="")
public class SmAgentApplyParam implements Serializable {

    @ApiModelProperty("省编码")
    @NotNull
    private Integer provinceCode;
    @ApiModelProperty("市编码")
    @NotNull
    private Integer cityCode;
    @NotBlank
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("联系电话")
    @NotBlank
    private String linkMobile;
    @ApiModelProperty("邮箱")
    @NotBlank
    private String email;
    @ApiModelProperty("身份证 正面")
    @NotBlank
    private String identityCardFront;
    @ApiModelProperty("身份证 反面")
//    @NotBlank
    private String identityCardBack;
    @ApiModelProperty("申请理由")
    private String remark;

//    @NotBlank
    @ApiModelProperty("身份证号")
    private String identityCardNo;


    @ApiModelProperty("验证码")
    @NotBlank
    private String authCode;
;

}
