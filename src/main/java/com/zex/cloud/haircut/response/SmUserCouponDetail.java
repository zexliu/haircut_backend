package com.zex.cloud.haircut.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.entity.SmShopCoupon;
import com.zex.cloud.haircut.entity.SmUserCoupon;
import com.zex.cloud.haircut.enums.CouponMemberStatus;
import com.zex.cloud.haircut.enums.CouponPublishType;
import com.zex.cloud.haircut.enums.CouponType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SmUserCouponDetail  {

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long shopId;

    private LocalDateTime createAt;

    private Boolean useStatus;

    private LocalDateTime useStartAt;

    private LocalDateTime useEndAt;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long couponId;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long userId;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long orderId;

    private String name;

    private String description;

    private BigDecimal amount;

    private CouponType couponType;

    private BigDecimal limitMin;

    private CouponPublishType publishType;

    private CouponMemberStatus memberStatus;

}
