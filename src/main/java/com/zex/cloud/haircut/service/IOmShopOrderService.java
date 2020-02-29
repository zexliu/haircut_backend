package com.zex.cloud.haircut.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zex.cloud.haircut.entity.OmShopOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.OmOrderParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
public interface IOmShopOrderService extends IService<OmShopOrder> {

    String validOrderAndCreate(OmOrderParam param, Long userId, Long orderId) throws JsonProcessingException;

    void cancelOrder(Long id);

    void onPayHook(Long orderId);

    void refund(Long id, Long userId);

    void validUseStatus(Long id, Long userId);

    void use(Long id, Long shopId);

    OmShopOrder comment(Long shopOrderId, Long userId);

}
