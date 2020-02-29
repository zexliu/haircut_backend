package com.zex.cloud.haircut.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.OrderStatus;
import com.zex.cloud.haircut.enums.OrderType;
import com.zex.cloud.haircut.enums.PayChannelType;
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
@ApiModel(value="OmOrder对象", description="")
public class OmOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    private OrderType orderType;

    private LocalDateTime payAt;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long userId;

    private BigDecimal amount;

    private PayChannelType channelType;

    private OrderStatus status;

    private String subject;

    private String body;

    private String ipAddress;

    private LocalDateTime createAt;

    private LocalDateTime expireAt;

    private String thirdPartyId;


}
