package com.zex.cloud.haircut.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.SexType;
import com.zex.cloud.haircut.enums.ShopOrderStatus;
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
 * @since 2020-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OmShopOrder对象", description="")
public class OmShopOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long shopId;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long userId;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long stylistId;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long orderId;

    private BigDecimal totalAmount;

    private BigDecimal realAmount;

    private ShopOrderStatus status;

    private LocalDateTime createAt;

    private LocalDateTime useAt;

    private LocalDateTime appointmentAt;

    private LocalDateTime expireAt;

    private String subject;

    private String body;

   private SexType sexType;


}
