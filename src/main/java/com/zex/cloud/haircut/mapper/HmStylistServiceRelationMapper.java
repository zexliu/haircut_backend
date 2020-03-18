package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.entity.HmStylistServiceRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface HmStylistServiceRelationMapper extends BaseMapper<HmStylistServiceRelation> {

    BigDecimal getMinAmount(@Param("serviceId") Long serviceId, @Param("shopId") Long shopId,@Param("stylistId")Long stylistId);

}
