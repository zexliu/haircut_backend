package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.enums.ShopTitleType;
import com.zex.cloud.haircut.vo.HmStylistCollectVO;
import com.zex.cloud.haircut.vo.ShopDetailVO;
import com.zex.cloud.haircut.vo.SmHomeShopVO;
import com.zex.cloud.haircut.vo.SmShopVO;
import com.zex.cloud.haircut.entity.SmShop;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SmHalfTimeParam;
import com.zex.cloud.haircut.params.SmShopCurrentParam;
import com.zex.cloud.haircut.params.SmShopParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
@RestController
@RequestMapping("/api/v1/shops")
@Api(tags = "店铺操作接口")
public class SmShopController {

    @Autowired
    private ISmShopService iSmShopService;

    @GetMapping
    @ApiOperation("获取店铺列表")
    public IPage<SmShop> list(Pageable pageable, String keywords, ShopWorkStatus workStatus, String provinceCode, String cityCode, String districtCode, Double longitude, Double latitude) {
        return iSmShopService.list(pageable.convert(), keywords, workStatus, provinceCode, cityCode, districtCode, longitude, latitude);
    }

    @PostMapping
    @ApiOperation("创建店铺")
    public SmShop create(@RequestBody @Valid SmShopParam param) {
        SmShop smShop = new SmShop();
        BeanUtils.copyProperties(param, smShop);
        return iSmShopService.customSave(smShop);
    }

    @PutMapping("/{id}")
    @ApiOperation("管理员修改店铺")
    public SmShop adminUpdate(@PathVariable Long id, @RequestBody @Valid SmShopParam param) {
        return iSmShopService.update(id, param);

    }

    @PutMapping("/current")
    @ApiOperation("店主修改店铺信息")
    public SmShop update(@RequestBody @Valid SmShopCurrentParam param) {
        return iSmShopService.updateCurrent(RequestHolder.user().getShopId(), param);
    }

    @GetMapping("/current")
    @ApiOperation("店铺管理员获取店铺信息")
    public SmShopVO current() {
        return iSmShopService.getByShopId(RequestHolder.user().getShopId());
    }


    @PutMapping("/workStatus")
    @ApiOperation("店主修改店铺工作状态")
    public SmShop updateWorkStatus(@RequestParam ShopWorkStatus workStatus) {
        return iSmShopService.currentWorkStatus(RequestHolder.user().getShopId(), workStatus);
    }

    @GetMapping("/{id}")
    @ApiOperation("查询店铺信息")
    public SmShop detail(@PathVariable Long id) {
        return iSmShopService.getById(id);
    }


    @PutMapping("/title/current")
    @ApiOperation("店主设置店铺服务标准")
    public String updateTitle(@RequestBody List<Long> titleIds) {
        iSmShopService.updateTitle(RequestHolder.user().getShopId(), titleIds);
        return SimpleResp.SUCCESS;
    }

    @PutMapping("/halfStatus/current")
    @ApiOperation("店主设置半价状态")
    public String updateHalfTime(@RequestParam Boolean halfStatus) {
        iSmShopService.updateHalfStatus(RequestHolder.user().getShopId(), halfStatus);
        return SimpleResp.SUCCESS;
    }

//    @PutMapping("/half/current")
//    @ApiOperation("店主设置半价开放时间")
//    public String updateHalfTime(@RequestBody List<SmHalfTimeParam> params) {
//        iSmShopService.updateHalfTime(RequestHolder.user().getShopId(), params);
//        return SimpleResp.SUCCESS;
//    }

//    @PutMapping("/service/discount")
//    @ApiOperation("店主设置半价开放时间")
//    public String serviceDiscount(){
//
//    }


    @GetMapping("/home")
    @ApiOperation("客户端首页获取店铺列表")
    public IPage<SmHomeShopVO> homeVo(Pageable pageable, String keywords, ShopWorkStatus workStatus, String provinceCode, String cityCode, String districtCode, @RequestParam Double longitude, @RequestParam Double latitude) {
        return iSmShopService.homeVo(pageable.convert(), keywords, workStatus, provinceCode, cityCode, districtCode, longitude, latitude);
    }


    @GetMapping("detail/{id}")
    @ApiOperation("客户端获取店铺详情")
    public ShopDetailVO shopDetailVO(@PathVariable Long id) {
        return iSmShopService.detail(id,RequestHolder.user().getId());
    }


    @GetMapping("/collect")
    @ApiOperation("获取收藏店铺")
    public IPage<SmHomeShopVO>  collectShops(Pageable pageable, @RequestParam Double latitude ,
                                                     @RequestParam Double longitude){
        return iSmShopService.getCollectShops(pageable.convert(),RequestHolder.user().getId(),latitude,longitude);
    }
}
