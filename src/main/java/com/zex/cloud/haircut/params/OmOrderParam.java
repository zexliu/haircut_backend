package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.OrderType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data

@ApiModel("订单请求参数")
public class OmOrderParam {

    @NotNull
    private OrderType orderType;
    @NotNull
    private BigDecimal amount;

    private String subject;

    private OmShopOrderBodyParam shopOrderBody;

    private OmShopGrouponBodyParam shopGrouponBody;

    private OmFlowerOrderBodyParam flowerOrderBody;

    private OmUserRewardBodyParam rewardBody;


}
