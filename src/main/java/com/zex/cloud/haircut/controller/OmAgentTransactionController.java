package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.OmAgentTransaction;
import com.zex.cloud.haircut.entity.OmPlatformTransaction;
import com.zex.cloud.haircut.entity.SmAgent;
import com.zex.cloud.haircut.enums.AgentTransactionType;
import com.zex.cloud.haircut.enums.PlatformTransactionType;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.WithDrawParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IOmAgentTransactionService;
import com.zex.cloud.haircut.service.ISmAgentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
@RestController
@RequestMapping("/api/v1/agent/transaction")
@Api(tags = "代理商交易记录")
public class OmAgentTransactionController {

    @Autowired
    private IOmAgentTransactionService iOmAgentTransactionService;

    @Autowired
    private ISmAgentService iSmAgentService;
    @PostMapping("/withdrawal")
    public String withdrawal(@RequestBody @Valid WithDrawParam param){
        SmAgent agent = iSmAgentService.getByUserId(RequestHolder.user().getId());
        iOmAgentTransactionService.withdrawal(param, agent.getId());
        return SimpleResp.SUCCESS;
    }


    @GetMapping("/current")
    public IPage<OmAgentTransaction> page(Pageable pageable, AgentTransactionType type, Boolean incrStatus) {
        SmAgent agent = iSmAgentService.getByUserId(RequestHolder.user().getId());

        return iOmAgentTransactionService.page(pageable.convert(), new LambdaQueryWrapper<OmAgentTransaction>()
                .eq(type != null, OmAgentTransaction::getType, type)
                .eq(incrStatus != null, OmAgentTransaction::getIncrStatus, incrStatus)
                .eq(OmAgentTransaction::getAgentId,agent.getId())
                .orderByDesc(OmAgentTransaction::getCreateAt));
    }



}
