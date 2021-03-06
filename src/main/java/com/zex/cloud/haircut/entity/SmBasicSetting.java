package com.zex.cloud.haircut.entity;

import java.math.BigDecimal;
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
 * @since 2020-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SmBasicSetting对象", description="")
public class SmBasicSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    private BigDecimal shopCommissionProportion;

    private BigDecimal userCommissionProportion;

    private BigDecimal userFirstAmount;

    private LocalDateTime createAt;

    private String operatorIp;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long operatorId;

    private LocalDateTime operatorAt;

    private Integer shopFreeMonth;

    private BigDecimal agentCommissionProportion;


}
