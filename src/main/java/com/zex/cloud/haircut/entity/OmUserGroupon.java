package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.enums.UserGrouponStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="OmUserGroupon对象", description="")
public class OmUserGroupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long userId;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long shopId;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long stylistId;

    private LocalDateTime createAt;

    private LocalDateTime expireAt;

    private Integer totalCount;

    private Integer remainCount;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long serviceId;

    private BigDecimal amount;

    private UserGrouponStatus status;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long  orderId;

    private GenderType genderType;
}
