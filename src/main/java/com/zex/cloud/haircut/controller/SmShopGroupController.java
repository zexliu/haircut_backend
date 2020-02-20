package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmShopGroup;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SmShopGroupParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmShopGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
@RestController
@Api(tags = "店铺团购信息操作接口")
@RequestMapping("/api/v1/shop/group")
public class SmShopGroupController {

    @Autowired
    private ISmShopGroupService iSmShopGroupService;

    @GetMapping
    @ApiOperation("通过店铺ID获取店铺团购信息")
    public IPage<SmShopGroup> page(Pageable pageable,Long shopId){
        return iSmShopGroupService.page(pageable.convert(),
                new LambdaQueryWrapper<SmShopGroup>()
                        .eq(shopId != null,SmShopGroup::getShopId,shopId));
    }

    @PostMapping
    @ApiOperation("新增店铺团购信息")
    public SmShopGroup create(@RequestBody SmShopGroupParam param){
        return iSmShopGroupService.create(param,RequestHolder.user().getShopId());
    }

    @PutMapping("/{id}")
    @ApiOperation("修改店铺团购信息")
    public SmShopGroup update(@PathVariable  Long id ,@RequestBody SmShopGroupParam param){
        return iSmShopGroupService.update(id,param,RequestHolder.user().getShopId());
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除店铺团购信息")
    public String delete(@PathVariable  Long id ){
         iSmShopGroupService.delete(id,RequestHolder.user().getShopId());
         return SimpleResp.SUCCESS;
    }

}
