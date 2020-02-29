package com.zex.cloud.haircut.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OmOrderServiceParam {

    //服务ID
    @ApiModelProperty(value = "所选服务ID")
    Long serviceId;

    //是否半价
    @ApiModelProperty(value = "是否半价")
    Boolean isHalf;

    //用户折扣
    @ApiModelProperty(value = "折扣ID")
    Long discountId;

    @ApiModelProperty(value = "原价")
    BigDecimal originalAmount;

    @ApiModelProperty(value = "实际金额")
    BigDecimal realAmount;


}
