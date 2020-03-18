package com.zex.cloud.haircut.vo;

import com.zex.cloud.haircut.entity.HmStylist;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class HmStylistCollectVO extends HmStylist {

    @ApiModelProperty("等待人数")
    private Integer waitCount;
    @ApiModelProperty("洗剪吹价格")
    private BigDecimal price;
    @ApiModelProperty("半价状态")
    private Boolean halfStatus;
    @ApiModelProperty("等待时间")
    private Integer waitTime;
    @ApiModelProperty("手艺值")
    private  Integer skillPraise;
    @ApiModelProperty("距离")
    private Double distance;
    private String shopName;

}
