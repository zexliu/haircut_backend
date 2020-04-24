package com.zex.cloud.haircut.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SmShopDiscountParam {

    private String name;

    @NotNull
    private Long serviceId;

    @NotNull
    private BigDecimal discount;

}
