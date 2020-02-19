package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmShop;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SmShopParam;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmShopService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/admin/{id}")
    @ApiOperation("管理员修改店铺")
    public SmShop adminUpdate (@PathVariable Long id,@RequestBody SmShopParam param){
        return iSmShopService.adminUpdate(id,param);

    }

    @PutMapping("/{id}")
    @ApiOperation("修改店铺信息")
    public SmShop update (@PathVariable Long id,@RequestBody SmShopParam param){
        return iSmShopService.update(id,param, RequestHolder.user().getId());
    }


}
