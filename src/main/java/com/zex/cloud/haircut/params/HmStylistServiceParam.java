package com.zex.cloud.haircut.params;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HmStylistServiceParam {

    Long serviceId;

    private BigDecimal malePrice;

    private BigDecimal femalePrice;

}
