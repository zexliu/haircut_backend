package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.OrderType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

@Data

@ApiModel("订单请求参数")
public class OmOrderParam {

    private OrderType orderType;

    private BigDecimal amount;

    private String subject;

    private OmShopOrderBodyParam shopOrderBody;

    private OmShopGrouponBodyParam shopGrouponBody;

    private OmFlowerOrderBodyParam flowerOrderBody;

    private OmUserRewardBodyParam rewardBody;


}
