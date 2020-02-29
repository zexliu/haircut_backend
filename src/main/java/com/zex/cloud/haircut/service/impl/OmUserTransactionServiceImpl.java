package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.entity.OmUserTransaction;
import com.zex.cloud.haircut.enums.UserTransactionType;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.mapper.OmUserTransactionMapper;
import com.zex.cloud.haircut.service.IOmRefundOrderService;
import com.zex.cloud.haircut.service.IOmUserTransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.util.DecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
@Service
public class OmUserTransactionServiceImpl extends ServiceImpl<OmUserTransactionMapper, OmUserTransaction> implements IOmUserTransactionService {

    @Autowired
    private IOmRefundOrderService iOmRefundOrderService;
    @Override
    public void onPayHook(OmOrder omOrder) {
        OmUserTransaction transaction = new OmUserTransaction();
        transaction.setOrderId(omOrder.getId());
        transaction.setAmount(omOrder.getAmount());
        transaction.setIncrStatus(true);
        transaction.setUserId(omOrder.getUserId());
        transaction.setType(UserTransactionType.CHARGE);
        save(transaction);
    }

    @Override
    public void payOrder(Long id, BigDecimal amount, Long userId) {
        BigDecimal balance = baseMapper.balance(null, null, null, null, userId);
        if (DecimalUtils.lt(balance,amount)){
            throw new ForbiddenException("账户余额不足");
        }
        OmUserTransaction transaction = new OmUserTransaction();
        transaction.setOrderId(id);
        transaction.setAmount(amount);
        transaction.setIncrStatus(false);
        transaction.setUserId(userId);
        transaction.setType(UserTransactionType.SHOPPING);
        save(transaction);
    }

    @Override
    public IPage<OmUserTransaction> page(Page<OmUserTransaction> convert, LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, UserTransactionType transactionType, Long userId) {
        return page(convert, new LambdaQueryWrapper<OmUserTransaction>()
                .ge(startAt != null, OmUserTransaction::getCreateAt, startAt)
                .ge(userId != null, OmUserTransaction::getUserId, userId)
                .le(endAt != null, OmUserTransaction::getCreateAt, endAt)
                .eq(incrStatus != null, OmUserTransaction::getIncrStatus, incrStatus)
                .eq(transactionType != null, OmUserTransaction::getType, transactionType));
    }

    @Override
    public BigDecimal balance(LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, UserTransactionType transactionType, Long userId) {
        return baseMapper.balance(startAt,endAt,incrStatus,transactionType,userId);
    }

    @Override
    public void refund(Long id, Long userId, BigDecimal refundAmount, Long refundId) {
        OmUserTransaction transaction = new OmUserTransaction();
        transaction.setOrderId(id);
        transaction.setAmount(refundAmount);
        transaction.setIncrStatus(true);
        transaction.setUserId(userId);
        transaction.setType(UserTransactionType.REFUND);
        save(transaction);
        iOmRefundOrderService.onRefundHook(refundId,refundAmount,String.valueOf(transaction.getId()));
    }


}
