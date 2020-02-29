package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.OmRefundOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.PayChannelType;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
public interface IOmRefundOrderService extends IService<OmRefundOrder> {

    Long create(Long id, Long userId, BigDecimal amount, String description, PayChannelType channelType);

    void onRefundHook(Long refundId, BigDecimal amount, String tradeNo);

}
