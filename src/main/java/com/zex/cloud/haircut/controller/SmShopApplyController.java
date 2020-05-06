package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmShopApply;
import com.zex.cloud.haircut.enums.AuditStatus;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SmShopApplyParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.response.SmShopApplyDetail;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmShopApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
@RestController
@Api(tags = "店铺入驻申请相关接口")
@RequestMapping("/api/v1/shop/apply")
public class SmShopApplyController {


    @Autowired
    private ISmShopApplyService iSmShopApplyService;

    @GetMapping("/current")
    @ApiOperation("获取当前用户的申请记录")
    public SmShopApply current() {
        return iSmShopApplyService.getApplyByUserId(RequestHolder.user().getId());
    }


    @PostMapping("/code")
    @ApiOperation("发送短信验证码")
    public String sendAuthCode(@RequestParam String mobile){
        iSmShopApplyService.sendAuthCode(mobile);
        return SimpleResp.SUCCESS;
    }

    @PostMapping
    @ApiOperation("提交申请")
    public SmShopApply create(@RequestBody @Valid SmShopApplyParam param) {
        return iSmShopApplyService.create(param, RequestHolder.user().getId());
    }

    @PutMapping
    @ApiOperation("修改申请(驳回后重新申请)")
    public SmShopApply update(@RequestBody @Valid SmShopApplyParam param) {
        return iSmShopApplyService.update(param, RequestHolder.user().getId());
    }


    @GetMapping
    @ApiOperation("申请列表")
    public IPage<SmShopApply> list(Pageable page, AuditStatus auditStatus, String keywords) {
        return iSmShopApplyService.page(page.convert(), new LambdaQueryWrapper<SmShopApply>()
                .eq(auditStatus != null, SmShopApply::getAuditStatus, auditStatus)
                .eq(StringUtils.isNotBlank(keywords), SmShopApply::getName, keywords)
                .orderByAsc(SmShopApply::getUpdateAt));
    }


    @GetMapping("/{id}")
    @ApiOperation("申请详情")
    public SmShopApplyDetail detail(@PathVariable Long id){
        return iSmShopApplyService.detail(id);
    }



}
