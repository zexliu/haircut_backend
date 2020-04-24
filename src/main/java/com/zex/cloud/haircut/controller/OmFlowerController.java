package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.enums.FlowerType;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IOmFlowerService;
import com.zex.cloud.haircut.vo.OmFlowerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/v1/flower")
@Api(tags = "鲜花相关")
public class OmFlowerController {

    @Autowired
    private IOmFlowerService iOmFlowerService;

    @ApiOperation("获取鲜花分页")
    @GetMapping
    public IPage<OmFlowerVo> flowerVoIPage(Pageable pageable, Long userId, Long shopId, FlowerType type, LocalDateTime startAt, LocalDateTime endAt) {
        return iOmFlowerService.flowerVoPage(pageable.convert(), type, userId, shopId, startAt, endAt);
    }

    @ApiOperation("获取当前店铺鲜花分页")
    @GetMapping("/shop/current")
    public IPage<OmFlowerVo> shopCurrent(Pageable pageable, Long userId, FlowerType type, LocalDateTime startAt, LocalDateTime endAt) {
        Long shopId = RequestHolder.user().getShopId();
        return iOmFlowerService.flowerVoPage(pageable.convert(), type, userId, shopId, startAt, endAt);
    }


}
