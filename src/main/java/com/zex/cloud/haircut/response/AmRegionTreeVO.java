package com.zex.cloud.haircut.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AmRegionTreeVO extends AbstractTree<AmRegionTreeVO>{
    private Integer level;
    private Integer adCode;
    private String name;
    private Integer shopCount;
}