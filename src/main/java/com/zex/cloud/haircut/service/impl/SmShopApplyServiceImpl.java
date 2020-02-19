package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SmShopApply;
import com.zex.cloud.haircut.enums.AuditStatus;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.mapper.SmShopApplyMapper;
import com.zex.cloud.haircut.params.SmShopApplyParam;
import com.zex.cloud.haircut.service.ISmShopApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
@Service
public class SmShopApplyServiceImpl extends ServiceImpl<SmShopApplyMapper, SmShopApply> implements ISmShopApplyService {

    @Override
    public SmShopApply create(SmShopApplyParam param, Long userId) {
        SmShopApply smShopApply = getApplyByUserId(userId);
        if (smShopApply != null) {
            throw new ExistsException("申请记录已存在");
        }
        smShopApply = new SmShopApply();
        BeanUtils.copyProperties(param, smShopApply);
        smShopApply.setAuditStatus(AuditStatus.PENDING);
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
        updateById(smShopApply);
        return smShopApply;
    }
}
