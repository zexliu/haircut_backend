package com.zex.cloud.haircut.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.CouponMemberStatus;
import com.zex.cloud.haircut.enums.CouponPublishType;
import com.zex.cloud.haircut.enums.CouponPullLimitStatus;
import com.zex.cloud.haircut.enums.CouponType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zex
 * @since 2020-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SmShopCoupon对象", description="")
public class SmShopCoupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long shopId;

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

    private Integer surplusCount;

    private LocalDateTime createAt;

    private Boolean absoluteStatus;

    private LocalDateTime absoluteStartAt;

    private LocalDateTime absoluteEndAt;

    private Integer relativeDay;


}
