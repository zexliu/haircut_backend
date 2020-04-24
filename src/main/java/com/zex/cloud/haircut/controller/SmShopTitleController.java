package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmShopTitle;
import com.zex.cloud.haircut.enums.ShopTitleType;
import com.zex.cloud.haircut.params.BaseTitleParam;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.ShopTitleParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.service.ISmShopTitleService;
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
@Api(tags = "店铺服务标准相关接口")
@RequestMapping("/api/v1/shops/titles")
public class SmShopTitleController {

    @Autowired
    private ISmShopTitleService iSmShopTitleService;

    @GetMapping
    @ApiOperation("查询所有")
    public IPage<SmShopTitle> list(Pageable pageable,ShopTitleType type){
        return iSmShopTitleService.page(pageable.convert(),
                new LambdaQueryWrapper<SmShopTitle>()
                        .eq(type != null, SmShopTitle::getType,type)
                        .orderByDesc(SmShopTitle::getSeq));
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID查询")
    public SmShopTitle getById(@PathVariable Long id){
        return iSmShopTitleService.getById(id);
    }

    @PostMapping
    @ApiOperation("新增")
    public SmShopTitle save(@RequestBody ShopTitleParam param){
        return iSmShopTitleService.save(param);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改")
    public SmShopTitle update(@PathVariable Long id,@RequestBody ShopTitleParam param){
        return iSmShopTitleService.update(id,param);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public String delete(@PathVariable Long id){
         iSmShopTitleService.delete(id);
        return SimpleResp.SUCCESS;
    }
}
