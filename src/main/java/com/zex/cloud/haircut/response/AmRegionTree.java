package com.zex.cloud.haircut.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AmRegionTree extends AbstractTree<AmRegionTree>{
    private Integer level;
    private Integer adCode;
    private String name;
}