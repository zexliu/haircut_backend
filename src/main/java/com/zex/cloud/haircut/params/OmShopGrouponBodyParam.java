package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.SexType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    private SexType sexType;

    private Long couponId;
}

