package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.PopularizeStatus;
import com.zex.cloud.haircut.enums.PopularizeType;
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
 * @since 2020-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UmPopularize对象", description="")
public class UmPopularize implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long userId;

    private PopularizeType targetType;

    private Long targetId;

    private PopularizeStatus status;

    private LocalDateTime createAt;

    private LocalDateTime firstPayAt;

    private BigDecimal amount;

}
