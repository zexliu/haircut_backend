package com.zex.cloud.haircut.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

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
 * @since 2020-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OmShopTransaction对象", description="")
public class OmShopTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long targetId;

    private Long shopId;

    private BigDecimal amount;

    private ShopTransactionType type;

    private Boolean incrStatus;

    private LocalDateTime createAt;


}
