package com.zex.cloud.haircut.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class WithDrawParam {


    @NotNull
    private BigDecimal amount;
    @NotBlank

    private String brandName;
    @NotBlank

    private String brandNo;
    @NotBlank

    private String brandUsername;

    private String brandOpening;
}
