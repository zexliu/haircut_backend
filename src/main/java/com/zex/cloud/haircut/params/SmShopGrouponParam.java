package com.zex.cloud.haircut.params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.GenderType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SmShopGrouponParam {

    private String name;

    @JsonSerialize(using = JsonLongSerializer.class)
    @NotNull
    private Long serviceId;
    @NotNull
    private Integer count;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private GenderType gender;
}
