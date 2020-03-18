package com.zex.cloud.haircut.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class HmServiceGrouponVo {
    @ApiModelProperty("单价")
    private BigDecimal singlePrice;
    @ApiModelProperty("团购价")
    private BigDecimal grouponPrice;

    @ApiModelProperty("发型师服务ID 购买时用")
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long stylistServiceId;
    @ApiModelProperty("团购ID 购买时用")
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long grouponId;
    @ApiModelProperty("服务项名称")
    private String serviceName;

    @ApiModelProperty("服务项ID")
    @JsonSerialize(using = JsonLongSerializer.class)
    @NotNull
    private Long serviceId;

}
