package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.CouponMemberStatus;
import com.zex.cloud.haircut.enums.CouponPublishType;
import com.zex.cloud.haircut.enums.CouponPullLimitStatus;
import com.zex.cloud.haircut.enums.CouponType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SmShopCouponParam {
    private String name;

    private String description;

    private BigDecimal amount;

    private CouponType couponType;

    private BigDecimal limitMin;

    private CouponPublishType publishType;

    private CouponMemberStatus memberStatus;

    private CouponPullLimitStatus pullLimitStatus;

    private LocalDateTime pullStartAt;

    private LocalDateTime pullEndAt;

    private Boolean publishStatus;

    private Integer publishCount;

    private Boolean absoluteStatus;

    private LocalDateTime absoluteStartAt;

    private LocalDateTime absoluteEndAt;

    private Integer relativeDay;
}
