package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmCommissionDiscount;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SmCommissionDiscountParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmCommissionDiscountService;
import com.zex.cloud.haircut.util.NetWorkUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-01
 */
@RestController
@Api(tags = "店铺抽成折扣")
@RequestMapping("/api/v1/commission/discounts")
public class SmCommissionDiscountController {

    @Autowired
    private ISmCommissionDiscountService iSmCommissionDiscountService;

    @PostMapping
    @ApiOperation("新增")
    public SmCommissionDiscount save(@Valid @RequestBody SmCommissionDiscountParam param, HttpServletRequest request){
        return iSmCommissionDiscountService.save(param, RequestHolder.user().getId(), NetWorkUtils.getRemoteHost(request));
    }

    @PutMapping("/{id}")
    @ApiOperation("更新")
    public SmCommissionDiscount update(@PathVariable Long id , @RequestBody @Valid SmCommissionDiscountParam param, HttpServletRequest request){
        return iSmCommissionDiscountService.update(id,param,RequestHolder.user().getId(),NetWorkUtils.getRemoteHost(request));
    }

    @GetMapping
    @ApiOperation("查询")
    public IPage<SmCommissionDiscount> list(Pageable pageable){
        return iSmCommissionDiscountService.page(pageable.convert(),new LambdaQueryWrapper<SmCommissionDiscount>()
                .orderByAsc(SmCommissionDiscount::getCount));
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public String delete(@PathVariable Long id){
        iSmCommissionDiscountService.removeById(id);
        return SimpleResp.SUCCESS;
    }



}
