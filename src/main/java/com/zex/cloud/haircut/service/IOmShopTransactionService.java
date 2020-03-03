package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.OmShopTransaction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.entity.OmUserTransaction;
import com.zex.cloud.haircut.enums.ShopTransactionType;
import com.zex.cloud.haircut.enums.UserTransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-03
 */
public interface IOmShopTransactionService extends IService<OmShopTransaction> {

    BigDecimal balance(LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, ShopTransactionType transactionType, Long shopId);

   IPage<OmShopTransaction> page(Page<OmShopTransaction> convert, LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, ShopTransactionType transactionType, Long shopId);

    void onReward(Long shopId, Long targetId, BigDecimal rewardAmount);
}
