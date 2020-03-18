package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.OmShopTransaction;
import com.zex.cloud.haircut.enums.ShopTransactionType;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IOmShopTransactionService;
import com.zex.cloud.haircut.vo.OmShopTransactionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-03
 */
@RestController
@RequestMapping("/api/v1/shop/transactions")
@Api(tags = "店铺交易记录接口")
public class OmShopTransactionController {



    @Autowired
    private IOmShopTransactionService iOmShopTransactionService;

    @GetMapping
    @ApiOperation("获取交易记录分页")
    public IPage<OmShopTransaction> page(Pageable pageable, LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, ShopTransactionType transactionType, Long shopId) {
        return iOmShopTransactionService.page(pageable.convert(),startAt,endAt,incrStatus,transactionType,shopId);

    }

    @GetMapping("/current")
    @ApiOperation("获取当前用户钱包记录分页")
    public IPage<OmShopTransaction> currentUserPage(Pageable pageable, LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, ShopTransactionType transactionType) {
        return iOmShopTransactionService.page(pageable.convert(),startAt,endAt,incrStatus,transactionType, RequestHolder.user().getShopId());

    }

    @GetMapping("/current/balance")
    @ApiOperation("获取当前用户钱包余额")
    public BigDecimal currentBalance(LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, ShopTransactionType transactionType){
        return iOmShopTransactionService.balance(startAt,endAt,incrStatus,transactionType,RequestHolder.user().getShopId());
    }

    @GetMapping("/balance")
    @ApiOperation("获取用户钱包余额")
    public BigDecimal currentBalance(LocalDateTime startAt, LocalDateTime endAt, Boolean incrStatus, ShopTransactionType transactionType, Long shopId){
        return iOmShopTransactionService.balance(startAt,endAt,incrStatus,transactionType,shopId);
    }


    @ApiOperation("获取当日成交金额 总金额 可提现金额")
    @GetMapping("/statistics/current")
    public OmShopTransactionVO currentShop(){
        return iOmShopTransactionService.currentShop(RequestHolder.user().getShopId());
    }

}
