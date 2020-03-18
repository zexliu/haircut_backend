package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zex.cloud.haircut.entity.OmShopOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.enums.ShopOrderStatus;
import com.zex.cloud.haircut.params.OmOrderParam;
import com.zex.cloud.haircut.vo.OmShopOrderVO;

import java.time.LocalDateTime;

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

    IPage<OmShopOrderVO> shopOrderVO(IPage<OmShopOrderVO> page, String keywords, Long shopId, Long stylistId, Long userId, ShopOrderStatus status, GenderType genderType, LocalDateTime startAt, LocalDateTime endAt, Boolean useStatus, Boolean payStatus);

    int getWaitCount(Long id, LocalDateTime now);

    int stylistOrderCount(Long id);

}
