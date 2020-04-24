package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.AmAuditHistory;
import com.zex.cloud.haircut.enums.AuditTargetType;
import com.zex.cloud.haircut.mapper.AmAuditHistoryMapper;
import com.zex.cloud.haircut.params.AmAuditParam;
import com.zex.cloud.haircut.service.AuditProcessor;
import com.zex.cloud.haircut.service.IAmAuditHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
@Service
public class AmAuditHistoryServiceImpl extends ServiceImpl<AmAuditHistoryMapper, AmAuditHistory> implements IAmAuditHistoryService {

    @Autowired
    private AuditProcessor iSmShopApplyService;

    @Autowired
    private AuditProcessor iSmAgentApplyService;

    @Autowired
    private AuditProcessor iomWithdrawalApplyService;

    @Override
    public List<AmAuditHistory> getByTargetIdAndType(Long targetId, AuditTargetType targetType) {
        return list(new LambdaQueryWrapper<AmAuditHistory>()
                .eq(AmAuditHistory::getTargetId, targetId)
                .eq(AmAuditHistory::getTargetType, targetType));
    }

    @Override
    @Transactional
    public void audit(AmAuditParam param, String operatorIp, Long operatorId) {

        String body = null;
        switch (param.getTargetType()) {
            case SHOP:
                body = iSmShopApplyService.auditProcess(param.getTargetId(), param.getAuditStatus());
                break;
            case AGENT:
                body = iSmAgentApplyService.auditProcess(param.getTargetId(), param.getAuditStatus());
                break;
            case WITHDRAWAL:
                body = iomWithdrawalApplyService.auditProcess(param.getTargetId(), param.getAuditStatus());
            default:
                break;
        }
        AmAuditHistory amAuditHistory = new AmAuditHistory();
        amAuditHistory.setMessage(param.getMessage());
        amAuditHistory.setOperatorId(operatorId);
        amAuditHistory.setOperatorIp(operatorIp);
        amAuditHistory.setSnapshot(body);
        amAuditHistory.setStatus(param.getAuditStatus());
        amAuditHistory.setTargetId(param.getTargetId());
        amAuditHistory.setTargetType(param.getTargetType());
        save(amAuditHistory);
    }


}
