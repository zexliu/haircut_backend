package com.zex.cloud.haircut.params;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SmCommissionDiscountParam {
    @NotNull
    private Integer count;
    @NotNull
    private BigDecimal discount;
}
