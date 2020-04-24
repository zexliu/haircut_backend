package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmAgentApply;
import com.zex.cloud.haircut.entity.SmShopApply;
import com.zex.cloud.haircut.enums.AuditStatus;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SmAgentApplyParam;
import com.zex.cloud.haircut.params.SmShopApplyParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.response.SmAgentApplyDetail;
import com.zex.cloud.haircut.response.SmShopApplyDetail;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmAgentApplyService;
import com.zex.cloud.haircut.service.ISmAgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.function.Consumer;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
@RestController
@Api(tags = "代理商申请相关接口")
@RequestMapping("/api/v1/agent/apply")
public class SmAgentApplyController {

    @Autowired
    private ISmAgentApplyService iSmAgentApplyService;

    @GetMapping("/current")
    @ApiOperation("获取当前用户的申请记录")
    public SmAgentApply current() {
        return iSmAgentApplyService.getApplyByUserId(RequestHolder.user().getId());
    }

    @PostMapping
    @ApiOperation("提交申请")
    public SmAgentApply create(@Valid @RequestBody SmAgentApplyParam param) {
        return iSmAgentApplyService.create(param, RequestHolder.user().getId());
    }


    @PostMapping("/code")
    @ApiOperation("发送短信验证码")
    public String sendAuthCode(@RequestParam String mobile){
         iSmAgentApplyService.sendAuthCode(mobile);
        return SimpleResp.SUCCESS;
    }
    @PutMapping
    @ApiOperation("修改申请(驳回后重新申请)")
    public SmAgentApply update( @Valid @RequestBody SmAgentApplyParam param) {
        return iSmAgentApplyService.update(param, RequestHolder.user().getId());
    }


    @GetMapping
    @ApiOperation("申请列表")
    public IPage<SmAgentApply> list(Pageable page, AuditStatus auditStatus, String keywords) {
        return iSmAgentApplyService.page(page.convert(), new LambdaQueryWrapper<SmAgentApply>()
                .and(StringUtils.isNotBlank(keywords), i -> i.like(SmAgentApply::getName,keywords).or().like(SmAgentApply::getLinkMobile,keywords))
                .eq(auditStatus != null, SmAgentApply::getAuditStatus, auditStatus)
                .orderByAsc(SmAgentApply::getUpdateAt));
    }


    @GetMapping("/{id}")
    @ApiOperation("申请详情")
    public SmAgentApplyDetail detail(@PathVariable Long id){
        return iSmAgentApplyService.detail(id);
    }

}
