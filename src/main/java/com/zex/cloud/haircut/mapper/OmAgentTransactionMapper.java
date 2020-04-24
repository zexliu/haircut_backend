package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.OmAgentTransaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
public interface OmAgentTransactionMapper extends BaseMapper<OmAgentTransaction> {

    BigDecimal income( @Param("id")Long id, @Param("startAt") LocalDate startAt,  @Param("endAt")LocalDate endAt);

    BigDecimal balance(@Param("id") Long id);

    List<BrokenLinePoint> brokenLines( @Param("startAt") LocalDate startAt,  @Param("endAt")LocalDate endAt,@Param("id") Long id);
}
