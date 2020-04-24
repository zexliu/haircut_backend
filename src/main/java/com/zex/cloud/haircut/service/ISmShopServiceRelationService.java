package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmShopServiceRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.ShopTitleType;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface ISmShopServiceRelationService extends IService<SmShopServiceRelation> {

    void removeByTitleId(Long id);

    void updateRelations(Long shopId, List<Long> titleIds);

    List<Long> getServiceIdsByShopId(Long shopId, ShopTitleType type);

}
