package com.zex.cloud.haircut.vo;

import com.zex.cloud.haircut.entity.HmStylist;
import com.zex.cloud.haircut.entity.HmStylistResume;
import com.zex.cloud.haircut.entity.HmWorkCase;
import com.zex.cloud.haircut.response.OmOrderCommentInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class HmStylistDetailVO extends HmStylist {
//    List<HmDomain> domains;
    List<HmWorkCase> workCases;
    List<HmStylistResume> resumes;
//    List<HmServiceGrouponVo> males;
//    List<HmServiceGrouponVo> females;

    @ApiModelProperty("店铺名称")
    private String shopName;
    //是否半价
    private Boolean halfStatus;
    private Long commentCount;
    private List<OmOrderCommentInfo> comments;
    private Float score;
    private Double distance;
    @ApiModelProperty("等待人数")
    private Integer waitCount;
    private BigDecimal price;
    @ApiModelProperty("等待时间")
    private Integer waitTime;
    private Boolean collect;
//    @ApiModelProperty("手艺值")
//    private  Integer skillPraise;
//    List<SmShopGroupon> groupons;
//    List<HmStylistServiceRelation> services;


}
