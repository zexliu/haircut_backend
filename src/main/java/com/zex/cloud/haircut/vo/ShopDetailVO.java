package com.zex.cloud.haircut.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonListLongSerializer;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.entity.HmStylist;
import com.zex.cloud.haircut.entity.SmShop;
import com.zex.cloud.haircut.response.OmOrderCommentInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShopDetailVO extends SmShop {

    private Integer fansCount;

    //发型师列表
    private List<HmStylistVO> stylists;

    //是否半价
    private Boolean halfStatus;

    private Long commentCount;

    @JsonSerialize(using = JsonListLongSerializer.class)
    private List<Long> defaultTitleIds;
    @JsonSerialize(using = JsonListLongSerializer.class)
    private List<Long> safetyTitleIds;

    private List<OmOrderCommentInfo> comments;

    private Boolean collect;
}
