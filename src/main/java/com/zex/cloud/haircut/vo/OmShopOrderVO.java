package com.zex.cloud.haircut.vo;

import com.zex.cloud.haircut.entity.OmShopOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OmShopOrderVO extends OmShopOrder {

    private String stylistName;

    private String userNickname;

}
