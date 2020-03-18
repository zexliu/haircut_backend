package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.entity.OmShopTransaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.enums.ShopTransactionType;
import com.zex.cloud.haircut.enums.UserTransactionType;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-03-03
 */
public interface OmShopTransactionMapper extends BaseMapper<OmShopTransaction> {

    BigDecimal balance(@Param("startAt") LocalDateTime startAt,
                       @Param("endAt") LocalDateTime endAt,
                       @Param("incrStatus") Boolean incrStatus,
                       @Param("type") ShopTransactionType transactionType,
                       @Param("shopId")  Long shopId);
}
