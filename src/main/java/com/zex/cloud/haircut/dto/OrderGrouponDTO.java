package com.zex.cloud.haircut.dto;

import com.zex.cloud.haircut.enums.GenderType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderGrouponDTO {
    private Long stylistId;
    private Long shopId;
    private Long serviceId;
    private Integer count;
    private BigDecimal amount;
    private GenderType genderType;
}
