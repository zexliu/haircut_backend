package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmShopDiscount;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SmShopDiscountParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.service.ISmShopDiscountService;
import com.zex.cloud.haircut.util.ValidableList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@Api(tags = "店铺单项服务折扣信息")
@RequestMapping("/api/v1/shop/discount")
public class SmShopDiscountController {

    @Autowired
    private ISmShopDiscountService iSmShopDiscountService;
    @GetMapping
    @ApiOperation("分页列表")
    public IPage<SmShopDiscount> list(Pageable pageable, Long shopId){
        return iSmShopDiscountService.page(pageable.convert(),
                new LambdaQueryWrapper<SmShopDiscount>()
        .eq(shopId != null,SmShopDiscount::getShopId,shopId));
    }

    @PostMapping
    @ApiOperation("新增店铺单项折扣")
    public SmShopDiscount create(@RequestBody SmShopDiscountParam param){
        return iSmShopDiscountService.create(param, RequestHolder.user().getShopId());
    }

    @PutMapping("/{id}")
    @ApiOperation("修改店铺单项折扣")
    public SmShopDiscount update(@PathVariable  Long id ,@RequestBody SmShopDiscountParam param){
        return iSmShopDiscountService.update(id,param,RequestHolder.user().getShopId());
    }


    @PostMapping("/batch")
    @ApiOperation("全量设置店铺单项折扣信息")
    public String  batch(@RequestBody @Valid ValidableList<SmShopDiscountParam> params){
        RequestUser requestUser  = RequestHolder.user();
         iSmShopDiscountService.batch(params,RequestHolder.user().getShopId());
        return SimpleResp.SUCCESS;
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除店铺单项折扣")
    public String delete(@PathVariable  Long id ){
        iSmShopDiscountService.delete(id,RequestHolder.user().getShopId());
        return SimpleResp.SUCCESS;

    }

}
