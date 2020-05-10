package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zex.cloud.haircut.entity.*;
import com.zex.cloud.haircut.enums.AuditStatus;
import com.zex.cloud.haircut.enums.AuditTargetType;
import com.zex.cloud.haircut.enums.AuthCodeType;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.ParameterException;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.mapper.SmAgentApplyMapper;
import com.zex.cloud.haircut.params.SmAgentApplyParam;
import com.zex.cloud.haircut.params.SmShopApplyParam;
import com.zex.cloud.haircut.response.SmAgentApplyDetail;
import com.zex.cloud.haircut.response.SmShopApplyDetail;
import com.zex.cloud.haircut.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
@Service("iSmAgentApplyService")
public class SmAgentApplyServiceImpl extends ServiceImpl<SmAgentApplyMapper, SmAgentApply> implements ISmAgentApplyService , AuditProcessor {

    @Autowired
    private ISmAgentService iSmAgentService;

    @Autowired
    private IAmAuditHistoryService iAmAuditHistoryService;


    @Autowired
    private ISmsService iSmsService;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public SmAgentApply getApplyByUserId(Long userId) {
        return getOne(new LambdaQueryWrapper<SmAgentApply>()
                .eq(SmAgentApply::getUserId, userId));
    }

    @Override
    public SmAgentApply create(SmAgentApplyParam param, Long userId) {

        SmAgentApply smAgentApply = getOne(new LambdaQueryWrapper<SmAgentApply>()
                .eq(SmAgentApply::getLinkMobile, param.getLinkMobile()));
        if (smAgentApply != null) {
            throw new ExistsException("该联系电话已经申请过了");
        }
        SmAgent smAgent = iSmAgentService.getOne(new LambdaQueryWrapper<SmAgent>()
                .eq(SmAgent::getProvinceCode, param.getProvinceCode()).eq(SmAgent::getCityCode, param.getCityCode()));
        if (smAgent != null) {
            throw new ExistsException("本地区已经有代理商了");
        }
        smAgentApply = getApplyByUserId(userId);
        if (smAgentApply != null) {
            throw new ExistsException("申请记录已存在");
        }

        if (!iSmsService.isAuthCodePass(param.getLinkMobile(),AuthCodeType.AGENT_APPLY,param.getAuthCode())){
            throw new ParameterException("验证码不正确或已过期");
        }
        smAgentApply = new SmAgentApply();
        BeanUtils.copyProperties(param, smAgentApply);
        smAgentApply.setAuditStatus(AuditStatus.PENDING);
        smAgentApply.setUpdateAt(LocalDateTime.now());
        smAgentApply.setUserId(userId);
        smAgentApply.setVersion(1);
        save(smAgentApply);

        return smAgentApply;
    }




    @Override
    public SmAgentApply update(SmAgentApplyParam param, Long userId) {
        SmAgentApply smAgentApply = getApplyByUserId(userId);
        if (smAgentApply.getAuditStatus() == AuditStatus.PENDING) {
            throw new ForbiddenException("正在审核中...,请等待");
        }
        if (smAgentApply.getAuditStatus() == AuditStatus.PASSED) {
            throw new ForbiddenException("该申请已通过...");
        }
        SmAgent smAgent = iSmAgentService.getOne(new LambdaQueryWrapper<SmAgent>()
                .eq(SmAgent::getProvinceCode, param.getProvinceCode()).eq(SmAgent::getCityCode, param.getCityCode()));
        if (smAgent != null) {
            throw new ExistsException("本地区已经有代理商了");
        }
        BeanUtils.copyProperties(param, smAgentApply);
        smAgentApply.setAuditStatus(AuditStatus.PENDING);
        smAgentApply.setVersion(smAgentApply.getVersion() + 1);
        smAgentApply.setUpdateAt(LocalDateTime.now());
        updateById(smAgentApply);
        return smAgentApply;
    }

    @Override
    public SmAgentApplyDetail detail(Long id) {
        SmAgentApply apply = getById(id);
        List<AmAuditHistory> histories = iAmAuditHistoryService.getByTargetIdAndType(id, AuditTargetType.AGENT);
        SmAgentApplyDetail detail  = new SmAgentApplyDetail();
        BeanUtils.copyProperties(apply,detail);
        detail.setAuditHistories(histories);
        return detail;
    }

    @Override
    public void sendAuthCode(String mobile) {
        iSmsService.sendAuthCode(mobile, AuthCodeType.AGENT_APPLY,null);
    }

    @Override
    public String auditProcess(Long id, AuditStatus auditStatus) {
        SmAgentApply smAgentApply = getById(id);
        if (smAgentApply.getAuditStatus() != AuditStatus.PENDING){
            throw new ForbiddenException("该条记录不在待审核状态");
        }
        if (auditStatus == AuditStatus.PASSED ){
            SmAgent smAgent = new SmAgent();
            BeanUtils.copyProperties(smAgentApply,smAgent);
            smAgent.setId(null);
            smAgent.setCreateAt(null);
            smAgent.setEnable(true);
            iSmAgentService.customSave(smAgent);
        }
        smAgentApply.setAuditStatus(auditStatus);
        updateById(smAgentApply);
        try {
            return objectMapper.writeValueAsString(smAgentApply);
        } catch (JsonProcessingException e) {
            throw new ServerException();
        }
    }
}
