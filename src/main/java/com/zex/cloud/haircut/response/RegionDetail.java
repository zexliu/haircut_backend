package com.zex.cloud.haircut.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("地区")
public class RegionDetail {

    @ApiModelProperty("省编码")
    private Integer provinceCode;
    @ApiModelProperty("市编码")
    private Integer cityCode;
    @ApiModelProperty("区编码")
    private Integer districtCode;


}
