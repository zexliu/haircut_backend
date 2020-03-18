package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.entity.OmUserTransaction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.UserTransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
public interface IOmUserTransactionService extends IService<OmUserTransaction> {

    void onPayHook(OmOrder omOrder);

    void payOrder(Long id, BigDecimal amount, Long userId);

    IPage<OmUserTransaction> page(Page<OmUserTransaction> convert, LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, UserTransactionType transactionType, Long userId);

    BigDecimal balance(LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, UserTransactionType transactionType, Long userId);

    void refund(Long id, Long userId, BigDecimal refundAmount, Long refundId);

    void onReward(Long userId, Long rewardId, BigDecimal amount);


}
