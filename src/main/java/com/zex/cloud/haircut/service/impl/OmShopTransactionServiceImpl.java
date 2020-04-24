package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.OmShopTransaction;
import com.zex.cloud.haircut.entity.OmUserTransaction;
import com.zex.cloud.haircut.entity.OmWithdrawalApply;
import com.zex.cloud.haircut.enums.AuditStatus;
import com.zex.cloud.haircut.enums.ClientTargetType;
import com.zex.cloud.haircut.enums.ShopTransactionType;
import com.zex.cloud.haircut.enums.UserTransactionType;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.mapper.OmShopTransactionMapper;
import com.zex.cloud.haircut.params.WithDrawParam;
import com.zex.cloud.haircut.service.IOmShopTransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.service.IOmWithdrawalApplyService;
import com.zex.cloud.haircut.util.DateTimeUtils;
import com.zex.cloud.haircut.util.DecimalUtils;
import com.zex.cloud.haircut.vo.OmShopTransactionRewardVO;
import com.zex.cloud.haircut.vo.OmShopTransactionVO;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IOmWithdrawalApplyService iOmWithdrawalApplyService;
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

    @Override
    public IPage<OmShopTransactionRewardVO> rewardPage(Page<OmShopTransactionRewardVO> page, LocalDateTime startAt, LocalDateTime endAt, Long shopId) {
        return baseMapper.rewardPage(page,startAt,endAt,shopId);
    }

    @Override
    public void withdrawal(WithDrawParam param, Long shopId) {
        BigDecimal balance = baseMapper.balance(null,null,null,null,shopId);
        if (DecimalUtils.lt(balance,param.getAmount())){
            throw new ServerException("账户金额低于提现金额");
        }
        OmWithdrawalApply omWithdrawalApply = new OmWithdrawalApply();
        omWithdrawalApply.setAmount(param.getAmount());
        omWithdrawalApply.setAuditStatus(AuditStatus.PENDING);
        omWithdrawalApply.setBrandName(param.getBrandName());
        omWithdrawalApply.setBrandNo(param.getBrandNo());
        omWithdrawalApply.setBrandOpening(param.getBrandOpening());
        omWithdrawalApply.setBrandUsername(param.getBrandUsername());
        omWithdrawalApply.setTargetType(ClientTargetType.SHOP);
        omWithdrawalApply.setTargetId(shopId);
        iOmWithdrawalApplyService.save(omWithdrawalApply);
        OmShopTransaction transaction = new OmShopTransaction();
        transaction.setShopId(shopId);
        transaction.setAmount(param.getAmount());
        transaction.setIncrStatus(false);
        transaction.setType(ShopTransactionType.WITHDRAW);
        transaction.setTargetId(omWithdrawalApply.getId());
        save(transaction);
    }
}
