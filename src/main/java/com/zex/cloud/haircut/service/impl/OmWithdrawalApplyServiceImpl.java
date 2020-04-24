package com.zex.cloud.haircut.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zex.cloud.haircut.entity.OmWithdrawalApply;
import com.zex.cloud.haircut.entity.SmAgent;
import com.zex.cloud.haircut.entity.SmAgentApply;
import com.zex.cloud.haircut.enums.AuditStatus;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.mapper.OmWithdrawalApplyMapper;
import com.zex.cloud.haircut.service.AuditProcessor;
import com.zex.cloud.haircut.service.IOmWithdrawalApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-21
 */
@Service("iomWithdrawalApplyService")
public class OmWithdrawalApplyServiceImpl extends ServiceImpl<OmWithdrawalApplyMapper, OmWithdrawalApply> implements IOmWithdrawalApplyService , AuditProcessor {

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public String auditProcess(Long id, AuditStatus auditStatus) {

        OmWithdrawalApply withdrawalApply = getById(id);
        if (withdrawalApply.getAuditStatus() != AuditStatus.PENDING){
            throw new ForbiddenException("该条记录不在待审核状态");
        }
        withdrawalApply.setAuditStatus(AuditStatus.PASSED);
        withdrawalApply.setAuditAt(LocalDateTime.now());
        updateById(withdrawalApply);
        try {
            return objectMapper.writeValueAsString(withdrawalApply);
        } catch (JsonProcessingException e) {
            throw new ServerException();
        }
    }
}
