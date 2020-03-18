package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.entity.SmShopServiceRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface SmShopServiceRelationMapper extends BaseMapper<SmShopServiceRelation> {

    List<Long> getServiceIdsByShopId(Long shopId);

}
