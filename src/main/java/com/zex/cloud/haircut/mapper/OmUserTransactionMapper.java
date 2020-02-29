package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.entity.OmUserTransaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
 * @since 2020-02-23
 */
public interface OmUserTransactionMapper extends BaseMapper<OmUserTransaction> {


    BigDecimal balance(@Param("startAt") LocalDateTime startAt,
                       @Param("endAt") LocalDateTime endAt,
                       @Param("incrStatus") Boolean incrStatus,
                       @Param("type") UserTransactionType transactionType,
                       @Param("userId")  Long userId);

}
