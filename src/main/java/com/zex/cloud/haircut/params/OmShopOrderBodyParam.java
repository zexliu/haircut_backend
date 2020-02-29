package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.SexType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OmShopOrderBodyParam {

    @NotBlank
    List<OmOrderServiceParam> services;

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
    SexType sexType;
}
