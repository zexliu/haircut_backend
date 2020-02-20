package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.ShopWorkStatus;
import lombok.Data;

import java.util.List;


@Data
public class HmStylistParam {

    private String avatar;

    private String nickname;

    private Long jobTitleId;

    private String introduction;

    private String coverImages;

    private String htmlIntro;

    private ShopWorkStatus workStatus;

    private List<Long> domainIds;

    private List<HmStylistServiceParam> services;

}
