package com.zex.cloud.haircut.vo;

import com.zex.cloud.haircut.entity.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class HmStylistDetail extends HmStylist {
    private List<HmDomain> domains;
    private  List<HmWorkCase> workCases;
    private List<HmStylistResume> resumes;
    private  List<HmServiceGrouponVo> males;
    private  List<HmServiceGrouponVo> females;

//    List<SmShopGroupon> groupons;
//    List<HmStylistServiceRelation> services;

}
