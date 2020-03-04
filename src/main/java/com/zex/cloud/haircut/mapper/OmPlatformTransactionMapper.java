package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.OmPlatformTransaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-03-03
 */
public interface OmPlatformTransactionMapper extends BaseMapper<OmPlatformTransaction> {

    BigDecimal income(@Param("startAt") LocalDate startAt, @Param("endAt")LocalDate endAt);

    List<BrokenLinePoint> brokenLines(@Param("startAt") LocalDate startAt, @Param("endAt") LocalDate endAt);

}
