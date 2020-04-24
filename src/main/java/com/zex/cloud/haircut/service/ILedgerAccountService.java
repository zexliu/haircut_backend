package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.enums.OrderType;

import java.math.BigDecimal;

public interface ILedgerAccountService {
    void account(Long id, BigDecimal realAmount, Long userId, Long shopId, OrderType orderType);
}
