package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.GenderType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OmShopOrderBodyParam {

//    @NotBlank
//    List<OmOrderServiceParam> services;


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

    @NotNull
    @ApiModelProperty(value = "店铺ID")
    Long shopId;
    //优惠券ID
    @ApiModelProperty(value = "优惠券ID")
    Long couponId;

    @ApiModelProperty(value = "预约时间")
    LocalDateTime appointmentAt;

    @NotNull
    @ApiModelProperty(value = "发型师ID")
    Long stylistId;

    @NotNull
    @ApiModelProperty(value = "性别")
    GenderType genderType;
}
