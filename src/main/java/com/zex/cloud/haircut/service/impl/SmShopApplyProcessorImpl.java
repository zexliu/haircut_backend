package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zex.cloud.haircut.entity.AmAuditHistory;
import com.zex.cloud.haircut.entity.SmShop;
import com.zex.cloud.haircut.entity.SmShopApply;
import com.zex.cloud.haircut.enums.AuditStatus;
import com.zex.cloud.haircut.enums.AuditTargetType;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.mapper.SmShopApplyMapper;
import com.zex.cloud.haircut.params.SmShopApplyParam;
import com.zex.cloud.haircut.response.SmShopApplyDetail;
import com.zex.cloud.haircut.service.AuditProcessor;
import com.zex.cloud.haircut.service.IAmAuditHistoryService;
import com.zex.cloud.haircut.service.ISmShopApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.service.ISmShopService;
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
 * @since 2020-02-19
 */
@Service
public class SmShopApplyProcessorImpl extends ServiceImpl<SmShopApplyMapper, SmShopApply> implements ISmShopApplyService , AuditProcessor {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IAmAuditHistoryService iAmAuditHistoryService;

    @Autowired
    private ISmShopService iSmShopService;
    @Override
    public SmShopApply create(SmShopApplyParam param, Long userId) {
        SmShopApply smShopApply = getApplyByUserId(userId);
        if (smShopApply != null) {
            throw new ExistsException("申请记录已存在");
        }
        smShopApply = new SmShopApply();
        BeanUtils.copyProperties(param, smShopApply);
        smShopApply.setAuditStatus(AuditStatus.PENDING);
        smShopApply.setUpdateAt(LocalDateTime.now());
        smShopApply.setUserId(userId);
        save(smShopApply);
        return smShopApply;
    }

    @Override
    public SmShopApply getApplyByUserId(Long userId) {
        return getOne(new LambdaQueryWrapper<SmShopApply>().eq(SmShopApply::getUserId, userId));
    }

    @Override
    public SmShopApply update(SmShopApplyParam param, Long userId) {
        SmShopApply smShopApply = getApplyByUserId(userId);
        if (smShopApply.getAuditStatus() == AuditStatus.PENDING) {
            throw new ForbiddenException("正在审核中...,请等待");
        }
        if (smShopApply.getAuditStatus() == AuditStatus.PASSED) {
            throw new ForbiddenException("该申请已通过...");
        }
        BeanUtils.copyProperties(param, smShopApply);
        smShopApply.setAuditStatus(AuditStatus.PENDING);
        smShopApply.setVersion(smShopApply.getVersion() + 1);
        smShopApply.setUpdateAt(LocalDateTime.now());
        updateById(smShopApply);
        return smShopApply;
    }

    @Override
    public SmShopApplyDetail detail(Long id) {
        SmShopApply apply = getById(id);
        List<AmAuditHistory> histories = iAmAuditHistoryService.getByTargetIdAndType(id, AuditTargetType.SHOP);
        SmShopApplyDetail detail  = new SmShopApplyDetail();
        BeanUtils.copyProperties(apply,detail);
        detail.setAuditHistories(histories);
        return detail;
    }



    @Override
    public String auditProcess(Long id, AuditStatus auditStatus)  {
        SmShopApply smShopApply = getById(id);
        if (smShopApply.getAuditStatus() != AuditStatus.PENDING){
            throw new ForbiddenException("该条记录不在待审核状态");
        }
        if (auditStatus == AuditStatus.PASSED ){
            SmShop smShop = new SmShop();
            BeanUtils.copyProperties(smShopApply,smShop);
            smShop.setId(null);
            smShop.setCreateAt(null);
            smShop.setWorkStatus(ShopWorkStatus.REST);
            smShop.setLogo(smShopApply.getPhoto());
            iSmShopService.customSave(smShop);
        }
        smShopApply.setAuditStatus(auditStatus);
        updateById(smShopApply);
        try {
            return objectMapper.writeValueAsString(smShopApply);
        } catch (JsonProcessingException e) {
           throw new ServerException();
        }

    }
}
