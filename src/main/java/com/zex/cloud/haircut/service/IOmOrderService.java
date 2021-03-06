package com.zex.cloud.haircut.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.OmOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.OrderType;
import com.zex.cloud.haircut.params.OmOrderParam;
import com.zex.cloud.haircut.response.WxJsResponse;
import com.zex.cloud.haircut.security.RequestUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
public interface IOmOrderService extends IService<OmOrder> {

    OmOrder createOrder(OmOrderParam param, String ip, RequestUser user) throws JsonProcessingException;

    void cancelOrder(Long id, Long userId);

    WxJsResponse createPayment(Long id, String ip);

    void onPayHook(String orderId, BigDecimal amount, String tradeNo);

    void walletPayment(Long id, String transactionPassword, Long userId);

    void refund(Long id, Long userId, String description);


    BigDecimal income(LocalDate startAt, LocalDate endAt, OrderType type);

    List<BrokenLinePoint> brokenLines(LocalDate startAt, LocalDate endAt, OrderType type);


    boolean checkFirstPay(Long userId);

}
