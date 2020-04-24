package com.zex.cloud.haircut.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zex.cloud.haircut.enums.ShopTransactionType;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class OmShopTransactionRewardVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long targetId;

    private Long shopId;

    private BigDecimal amount;

    private ShopTransactionType type;

    private Boolean incrStatus;

    private LocalDateTime createAt;

    private String nickname;

    private String avatar;


}
