package com.zex.cloud.haircut.vo;

import com.zex.cloud.haircut.entity.OmShopOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OmShopOrderDetailVO extends OmShopOrder {

    private String shopName;
    private String shopLogo;
    private String leaderName;
    private String leaderMobile;
    private String stylistName;

    private String userNickname;

    private String stylistAvatar;

    private Integer skillPraise;
}
