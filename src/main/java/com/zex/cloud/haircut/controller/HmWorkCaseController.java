package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.HmWorkCase;
import com.zex.cloud.haircut.params.HmWorkCaseParam;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IHmWorkCaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
@RestController
@Api(tags = "作品案例操作相关接口")
@RequestMapping("/api/v1/stylists/cases")
public class HmWorkCaseController {

    @Autowired
    private IHmWorkCaseService iHmWorkCaseService;

    @GetMapping
    @ApiOperation("查询")
    public IPage<HmWorkCase> page(Pageable pageable, Long shopId, Long stylistId){
        return iHmWorkCaseService.page(pageable.convert(),
                new LambdaQueryWrapper<HmWorkCase>()
        .eq(shopId != null,HmWorkCase::getShopId,shopId)
        .eq(stylistId != null,HmWorkCase::getStylistId,stylistId));
    }

    @PostMapping
    @ApiOperation("新增")
    public HmWorkCase save(@RequestBody HmWorkCaseParam param){
        return iHmWorkCaseService.save(param, RequestHolder.user().getShopId());
    }

    @PutMapping("/{id}")
    @ApiOperation("修改")
    public HmWorkCase update(@PathVariable Long id,@RequestBody HmWorkCaseParam param){
        return iHmWorkCaseService.update(id,param, RequestHolder.user().getShopId());
    }
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询")
    public HmWorkCase getById(@PathVariable Long id){
        return iHmWorkCaseService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public String delete(@PathVariable Long id){
        iHmWorkCaseService.delete(id, RequestHolder.user().getShopId());
        return SimpleResp.SUCCESS;
    }
}
