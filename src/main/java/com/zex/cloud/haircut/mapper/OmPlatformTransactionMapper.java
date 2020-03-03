package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.entity.OmPlatformTransaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface OmPlatformTransactionMapper extends BaseMapper<OmPlatformTransaction> {

    BigDecimal income(@Param("startAt") LocalDateTime startAt, @Param("endAt")LocalDateTime endAt);

}
