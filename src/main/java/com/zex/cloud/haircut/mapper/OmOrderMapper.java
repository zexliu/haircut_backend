package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.OmOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.enums.OrderStatus;
import com.zex.cloud.haircut.enums.OrderType;
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
 * @since 2020-02-23
 */
public interface OmOrderMapper extends BaseMapper<OmOrder> {

    BigDecimal income(@Param("startAt") LocalDate startAt, @Param("endAt") LocalDate endAt, @Param("status") OrderStatus status, @Param("type") OrderType type);

    List<BrokenLinePoint> brokenLines(@Param("startAt") LocalDate startAt, @Param("endAt") LocalDate endAt, @Param("status") OrderStatus status,@Param("type") OrderType type);
}
