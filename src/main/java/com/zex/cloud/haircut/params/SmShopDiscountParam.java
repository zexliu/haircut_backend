package com.zex.cloud.haircut.params;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SmShopDiscountParam {

    private String name;

    private Long serviceId;

    private Long shopId;

    private BigDecimal discount;

}
