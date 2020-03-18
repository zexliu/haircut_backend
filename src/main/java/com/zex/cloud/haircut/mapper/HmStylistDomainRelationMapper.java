package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.entity.HmDomain;
import com.zex.cloud.haircut.entity.HmStylistDomainRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface HmStylistDomainRelationMapper extends BaseMapper<HmStylistDomainRelation> {

    List<HmDomain> getByStylistId(@Param("stylistId") Long id);
}
