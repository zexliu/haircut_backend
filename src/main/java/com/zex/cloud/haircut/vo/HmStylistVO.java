package com.zex.cloud.haircut.vo;

import com.zex.cloud.haircut.entity.HmStylist;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class HmStylistVO extends HmStylist {

    @ApiModelProperty("等待人数")
    private Integer waitCount;
    private BigDecimal price;
    @ApiModelProperty("等待时间")
    private Integer waitTime;
    @ApiModelProperty("手艺值")
    private  Integer skillPraise;
    @ApiModelProperty("是否收藏")
    private  Boolean collect;
}
