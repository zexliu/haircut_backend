package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.GenderType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OmShopGrouponBodyParam {
//    @NotEmpty
//    private List<Long> grouponIds;

    @NotNull
    private Long grouponId;

    @NotNull
    private Long shopId;

    @NotNull
    private Long stylistId;

    @NotNull
    private GenderType genderType;

    private Long couponId;
}

