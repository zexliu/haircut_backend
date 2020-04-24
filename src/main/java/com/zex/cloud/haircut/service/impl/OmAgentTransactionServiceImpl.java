package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.OmAgentTransaction;
import com.zex.cloud.haircut.entity.OmWithdrawalApply;
import com.zex.cloud.haircut.enums.AgentTransactionType;
import com.zex.cloud.haircut.enums.AuditStatus;
import com.zex.cloud.haircut.enums.ClientTargetType;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.mapper.OmAgentTransactionMapper;
import com.zex.cloud.haircut.params.WithDrawParam;
import com.zex.cloud.haircut.service.IOmAgentTransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.service.IOmWithdrawalApplyService;
import com.zex.cloud.haircut.util.DecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
@Service
public class OmAgentTransactionServiceImpl extends ServiceImpl<OmAgentTransactionMapper, OmAgentTransaction> implements IOmAgentTransactionService {

    @Autowired
    private IOmWithdrawalApplyService iOmWithdrawalApplyService;
    @Override
    public BigDecimal income(Long id, LocalDate startAt,LocalDate endAt) {
        BigDecimal income =  baseMapper.income(id,startAt,endAt);
        if (income == null){
            return new BigDecimal("0");
        }
        return income;
    }

    @Override
    public BigDecimal balance(Long id) {
        BigDecimal income =  baseMapper.balance(id);
        if (income == null){
            return new BigDecimal("0");
        }
        return income;
    }

    @Override
    public List<BrokenLinePoint> brokenLines(LocalDate startAt, LocalDate endAt, Long id) {
        return baseMapper.brokenLines(startAt,endAt,id);
    }

    @Override
    @Transactional
    public void withdrawal(WithDrawParam param, Long id) {
        BigDecimal balance = baseMapper.balance(id);
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
        omWithdrawalApply.setTargetType(ClientTargetType.AGENT);
        omWithdrawalApply.setTargetId(id);
        iOmWithdrawalApplyService.save(omWithdrawalApply);

        OmAgentTransaction transaction = new OmAgentTransaction();
        transaction.setAgentId(id);
        transaction.setAmount(param.getAmount());
        transaction.setIncrStatus(false);
        transaction.setType(AgentTransactionType.WITHDRAW);
        transaction.setTargetId(omWithdrawalApply.getId());
        save(transaction);
    }
}
