package com.zex.cloud.haircut.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zex.cloud.haircut.entity.SmShopGroupon;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.OmOrderParam;
import com.zex.cloud.haircut.params.SmShopGrouponParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
public interface ISmShopGrouponService extends IService<SmShopGroupon> {
    SmShopGroupon create(SmShopGrouponParam param, Long shopId);

    SmShopGroupon update(Long id, SmShopGrouponParam param, Long shopId);

    void delete(Long id, Long shopId);

    String validOrder(OmOrderParam param, Long userId, Long orderId) throws JsonProcessingException;

}
