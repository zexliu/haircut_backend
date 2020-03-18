package com.zex.cloud.haircut.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import reactor.core.Fuseable;

import java.math.BigDecimal;

@Data
public class SmHomeShopVO {

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("是否半价")
    private Boolean halfStatus;
    @ApiModelProperty("店铺LOGO")
    private String logo;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("工作状态")
    private ShopWorkStatus workStatus;
    @ApiModelProperty("距离")
    private Double distance;
    @ApiModelProperty("好评率")
    private Float scoreRate;
    @ApiModelProperty("正在剪发人数")
    private Integer peopleCount;
    @ApiModelProperty("洗剪吹价格")
    private BigDecimal price;


}
