package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.OmPlatformTransaction;
import com.zex.cloud.haircut.enums.PlatformTransactionType;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.service.IOmPlatformTransactionService;
import io.swagger.annotations.Api;
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
 * @since 2020-03-03
 */
@RestController
@RequestMapping("/api/v1/platform/transactions")
@Api(tags = "平台交易")
public class OmPlatformTransactionController {


    @Autowired
    private IOmPlatformTransactionService iOmPlatformTransactionService;

    @GetMapping
    public IPage<OmPlatformTransaction> page(Pageable pageable, PlatformTransactionType type, Boolean incrStatus) {
        return iOmPlatformTransactionService.page(pageable.convert(), new LambdaQueryWrapper<OmPlatformTransaction>()
                .eq(type != null, OmPlatformTransaction::getType, type)
                .eq(incrStatus != null, OmPlatformTransaction::getIncrStatus, incrStatus)
                .orderByDesc(OmPlatformTransaction::getCreateAt));
    }

}
