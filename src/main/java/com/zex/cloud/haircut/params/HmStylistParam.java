package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.ShopWorkStatus;
import com.zex.cloud.haircut.vo.HmServiceGrouponVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;


@Data
public class HmStylistParam {

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("职称ID")
    private Long jobTitleId;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("图片集")
    private String coverImages;

    @ApiModelProperty("预留字段")
    private String htmlIntro;

    @ApiModelProperty("工作状态")
    private ShopWorkStatus workStatus;

    @ApiModelProperty("擅长领域")
    private List<Long> domainIds;

//    @ApiModelProperty("服务收费")
//    private List<HmStylistServiceParam> services;

    @ApiModelProperty("工作履历")
    private List<HmStyListResumeParam> resumes;

    //    @ApiModelProperty("团购信息")
//    private List<SmShopGrouponParam> groupons;
    @ApiModelProperty("工作时长(年)")
    private Integer workAge;
    @ApiModelProperty("男数据")
    private List<HmServiceGrouponVo> males;
    @ApiModelProperty("女数据")
    private List<HmServiceGrouponVo> females;

    @ApiModelProperty("作品集")
    private List<HmWorkCaseParam> workCases;
}
