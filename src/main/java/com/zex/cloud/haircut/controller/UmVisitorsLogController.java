package com.zex.cloud.haircut.controller;


import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IUmVisitorsLogService;
import com.zex.cloud.haircut.vo.ShopVisitorsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-11
 */
@RestController
@Api(tags = "访客记录")
@RequestMapping("/api/v1/visitors")
public class UmVisitorsLogController {

    @Autowired
    private IUmVisitorsLogService iUmVisitorsLogService;
    @ApiOperation("获取店铺访客统计")
    @GetMapping("/statistics/current")
    public ShopVisitorsVO shopVisitorsVO(){
        return iUmVisitorsLogService.shopVisitors(RequestHolder.user().getShopId());
    }
}
