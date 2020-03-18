package com.zex.cloud.haircut.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OmShopTransactionVO {

   private BigDecimal todayAmount;

    private BigDecimal withdrawalAmount;

    private BigDecimal totalAmount;
}
