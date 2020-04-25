package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.PmBanner;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.PmBannerParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.service.IPmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-05
 */
@RestController
@RequestMapping("/api/v1/banners")
@Api(tags = "轮播图管理")
public class PmBannerController {

    @Autowired
    private IPmBannerService iPmBannerService;

    @GetMapping
    @ApiOperation("查询列表")
    public IPage<PmBanner> banners(Pageable pageable, Boolean enableStatus) {
        return iPmBannerService.page(pageable.convert(), new LambdaQueryWrapper<PmBanner>()
                .eq(enableStatus != null, PmBanner::getEnableStatus, enableStatus)
                .orderByDesc(PmBanner::getSeq));
    }


    @PostMapping
    @ApiOperation("新增")
    public PmBanner save(@RequestBody @Valid PmBannerParam param){
        return iPmBannerService.save(param);
    }


    @PutMapping("/{id}")
    @ApiOperation("更新")
    public PmBanner update(@PathVariable Long id ,@RequestBody @Valid PmBannerParam param){
        return iPmBannerService.update(id,param);
    }

    @PutMapping("/enableStatus/{id}")
    @ApiOperation("更新可用状态")
    public String enableStatus(@PathVariable Long id , @RequestParam Boolean enableStatus){
         iPmBannerService.enableStatus(id,enableStatus);

         return SimpleResp.SUCCESS;
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public String delete(@PathVariable Long id ){
        iPmBannerService.removeById(id);
        return SimpleResp.SUCCESS;
    }


    @GetMapping("/{id}")
    @ApiOperation("通过ID查询")
    public PmBanner getById(@PathVariable Long id ){
        return iPmBannerService.getById(id);
    }
}
