package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
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
@ApiModel(value="SmUserCoupon对象", description="")
public class SmUserCoupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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


}
