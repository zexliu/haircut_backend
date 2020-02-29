package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmShopGroupon;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SmShopGrouponParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmShopGrouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
@RestController
@Api(tags = "店铺团购信息操作接口")
@RequestMapping("/api/v1/shop/groupon")
public class SmShopGrouponController {

    @Autowired
    private ISmShopGrouponService iSmShopGroupService;
    @GetMapping
    @ApiOperation("通过店铺ID获取店铺团购信息")
    public IPage<SmShopGroupon> page(Pageable pageable, Long shopId){
        return iSmShopGroupService.page(pageable.convert(),
                new LambdaQueryWrapper<SmShopGroupon>()
                        .eq(shopId != null,SmShopGroupon::getShopId,shopId));
    }

    @PostMapping
    @ApiOperation("新增店铺团购信息")
    public SmShopGroupon create(@RequestBody SmShopGrouponParam param){
        return iSmShopGroupService.create(param, RequestHolder.user().getShopId());
    }

    @PutMapping("/{id}")
    @ApiOperation("修改店铺团购信息")
    public SmShopGroupon update(@PathVariable  Long id ,@RequestBody SmShopGrouponParam param){
        return iSmShopGroupService.update(id,param,RequestHolder.user().getShopId());
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除店铺团购信息")
    public String delete(@PathVariable  Long id ){
        iSmShopGroupService.delete(id,RequestHolder.user().getShopId());
        return SimpleResp.SUCCESS;
    }
}
