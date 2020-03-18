package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.OmShopTransaction;
import com.zex.cloud.haircut.enums.ShopTransactionType;
import com.zex.cloud.haircut.mapper.OmShopTransactionMapper;
import com.zex.cloud.haircut.service.IOmShopTransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.util.DateTimeUtils;
import com.zex.cloud.haircut.vo.OmShopTransactionVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-03
 */
@Service
public class OmShopTransactionServiceImpl extends ServiceImpl<OmShopTransactionMapper, OmShopTransaction> implements IOmShopTransactionService {

    @Override
    public BigDecimal balance(LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, ShopTransactionType transactionType, Long shopId) {
        return baseMapper.balance(startAt,endAt,incrStatus,transactionType,shopId);

    }

    @Override
    public IPage<OmShopTransaction> page(Page<OmShopTransaction> convert, LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, ShopTransactionType transactionType, Long shopId) {
        return page(convert, new LambdaQueryWrapper<OmShopTransaction>()
                .ge(startAt != null, OmShopTransaction::getCreateAt, startAt)
                .ge(shopId != null, OmShopTransaction::getShopId, shopId)
                .le(endAt != null, OmShopTransaction::getCreateAt, endAt)
                .eq(incrStatus != null, OmShopTransaction::getIncrStatus, incrStatus)
                .eq(transactionType != null, OmShopTransaction::getType, transactionType));
    }

    @Override
    public void onReward(Long shopId, Long targetId, BigDecimal rewardAmount) {
        OmShopTransaction transaction = new OmShopTransaction();
        transaction.setTargetId(targetId);
        transaction.setAmount(rewardAmount);
        transaction.setIncrStatus(true);
        transaction.setShopId(shopId);
        transaction.setType(ShopTransactionType.REWARD);
        save(transaction);
    }

    @Override
    public OmShopTransactionVO currentShop(Long shopId) {
        BigDecimal withdrawAmount = balance(null,null,null,null,shopId);
        BigDecimal todayAmount = balance(DateTimeUtils.LocalTimeToLocalDateTime(LocalDate.now()),DateTimeUtils.LocalTimeToLocalDateTime(LocalDate.now().plusDays(1)),true,null,shopId);
        BigDecimal totalAmount = balance(null,null,true,null,shopId);
        OmShopTransactionVO vo  = new OmShopTransactionVO();
        vo.setWithdrawalAmount(withdrawAmount);
        vo.setTotalAmount(totalAmount);
        vo.setTodayAmount(todayAmount);
        return vo;
    }
}
