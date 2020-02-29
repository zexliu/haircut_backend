package com.zex.cloud.haircut.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zex.cloud.haircut.entity.OmComment;
import com.zex.cloud.haircut.entity.OmOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.OmCommentOrderParam;
import com.zex.cloud.haircut.params.OmOrderParam;
import com.zex.cloud.haircut.response.WxJsResponse;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
public interface IOmOrderService extends IService<OmOrder> {

    OmOrder createOrder(OmOrderParam param, String ip, Long userId) throws JsonProcessingException;

    void cancelOrder(Long id, Long userId);

    WxJsResponse createPayment(Long id, String ip);

    void onPayHook(String orderId, BigDecimal amount, String tradeNo);

    void walletPayment(Long id, String transactionPassword, Long userId);

    void refund(Long id, Long userId);


}
