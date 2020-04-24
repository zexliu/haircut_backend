package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.OmWithdrawalApply;
import com.zex.cloud.haircut.enums.AuditStatus;
import com.zex.cloud.haircut.enums.ClientTargetType;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.service.IOmWithdrawalApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-21
 */
@RestController
@RequestMapping("/api/v1/withdrawal/apply")
@Api(tags = "提现申请")
public class OmWithdrawalApplyController {

    @Autowired
    private IOmWithdrawalApplyService iOmWithdrawalApplyService;

    @GetMapping
    public IPage<OmWithdrawalApply> page(Pageable pageable, AuditStatus auditStatus, ClientTargetType targetType) {
        return iOmWithdrawalApplyService.page(pageable.convert(), new LambdaQueryWrapper<OmWithdrawalApply>()
                .eq(auditStatus != null, OmWithdrawalApply::getAuditStatus, auditStatus)
                .eq(targetType != null, OmWithdrawalApply::getTargetType, targetType)
                .orderByDesc(OmWithdrawalApply::getCreateAt));
    }


}
