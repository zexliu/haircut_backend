package com.zex.cloud.haircut.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.AgentTransactionType;
import com.zex.cloud.haircut.enums.ShopTransactionType;
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
 * @since 2020-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OmAgentTransaction对象", description="")
public class OmAgentTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = JsonLongSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @JsonSerialize(using = JsonLongSerializer.class)

    private Long targetId;
    @JsonSerialize(using = JsonLongSerializer.class)

    private Long agentId;

    private BigDecimal amount;

    private AgentTransactionType type;

    private Boolean incrStatus;

    private LocalDateTime createAt;


}
