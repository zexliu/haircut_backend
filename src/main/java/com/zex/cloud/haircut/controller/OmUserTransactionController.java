package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.OmUserTransaction;
import com.zex.cloud.haircut.entity.SmAgent;
import com.zex.cloud.haircut.enums.UserTransactionType;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.WithDrawParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IOmUserTransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/api/v1/user/transactions")
@Api(tags = "用户交易记录接口")
public class OmUserTransactionController {

    @Autowired
    private IOmUserTransactionService iOmUserTransactionService;

    @GetMapping
    @ApiOperation("获取交易记录分页")
    public IPage<OmUserTransaction> page(Pageable pageable, LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, UserTransactionType transactionType, Long userId) {
       return iOmUserTransactionService.page(pageable.convert(),startAt,endAt,incrStatus,transactionType,userId);
    }

    @GetMapping("/current")
    @ApiOperation("获取当前用户钱包记录分页")
    public IPage<OmUserTransaction> currentUserPage(Pageable pageable, LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, UserTransactionType transactionType) {
        return iOmUserTransactionService.page(pageable.convert(),startAt,endAt,incrStatus,transactionType, RequestHolder.user().getId());

    }

    @GetMapping("/current/balance")
    @ApiOperation("获取当前用户钱包余额")
    public BigDecimal currentBalance(LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, UserTransactionType transactionType){
        return iOmUserTransactionService.balance(startAt,endAt,incrStatus,transactionType,RequestHolder.user().getId());
    }

    @GetMapping("/balance")
    @ApiOperation("获取用户钱包余额")
    public BigDecimal currentBalance(LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, UserTransactionType transactionType,Long userId){
        return iOmUserTransactionService.balance(startAt,endAt,incrStatus,transactionType,userId);
    }


    @PostMapping("/withdrawal")
    @ApiOperation("用户提现")
    public String withdrawal(@RequestBody @Valid WithDrawParam param){
        iOmUserTransactionService.withdrawal(param, RequestHolder.user().getId());
        return SimpleResp.SUCCESS;
    }
}
