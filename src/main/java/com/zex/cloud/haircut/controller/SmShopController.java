package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmShop;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SmShopParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmShopService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
@RestController
@RequestMapping("/api/v1/shops")
public class SmShopController {

    @Autowired
    private ISmShopService iSmShopService;


    @GetMapping
    @ApiOperation("获取店铺列表")
    public IPage<SmShop> list(Pageable pageable, String keywords, ShopWorkStatus workStatus,String provinceCode,String cityCode,String districtCode ,Double longitude,Double latitude){
        return iSmShopService.list(pageable.convert(),keywords,workStatus,provinceCode,cityCode,districtCode,longitude,latitude);
    }

    @PostMapping
    @ApiOperation("创建店铺")
    public SmShop create (@RequestBody SmShopParam param){
        SmShop smShop = new SmShop();
        BeanUtils.copyProperties(param,smShop);
        return iSmShopService.customSave(smShop);
    }

    @PutMapping("/{id}")
    @ApiOperation("管理员修改店铺")
    public SmShop adminUpdate (@PathVariable Long id,@RequestBody SmShopParam param){
        return iSmShopService.update(id,param);

    }

    @PutMapping("/current")
    @ApiOperation("店主修改店铺信息")
    public SmShop update (@RequestBody SmShopParam param){
        return iSmShopService.updateCurrent(RequestHolder.user().getShopId(),param);
    }

    @PutMapping("/workStatus")
    @ApiOperation("店主修改店铺工作状态")
    public SmShop updateWorkStatus (@RequestParam ShopWorkStatus workStatus){
        return iSmShopService.currentWorkStatus(RequestHolder.user().getShopId(),workStatus);
    }
    @GetMapping("/{id}")
    @ApiOperation("查询店铺信息")
    public SmShop detail (@PathVariable Long id){
        return iSmShopService.getById(id);
    }


    @PutMapping("/title/current")
    @ApiOperation("店主设置店铺服务标准")
    public String updateTitle(@RequestBody List<Long> titleIds){
         iSmShopService.updateTitle(RequestHolder.user().getShopId(),titleIds);
        return SimpleResp.SUCCESS;
    }

}
