package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmUserCoupon;
import com.zex.cloud.haircut.response.SmUserCouponDetail;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmUserCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-21
 */
@RestController
@Api("用户优惠券操作相关接口")
@RequestMapping("/api/v1/user/coupons")
public class SmUserCouponController {

    @Autowired
    private ISmUserCouponService iSmUserCouponService;

    @GetMapping("/current")
    @ApiOperation("获取当前用户的优惠券信息")
    public IPage<SmUserCouponDetail> page(Long shopId, @ApiParam("是否使用") Boolean useStatus,@ApiParam("是否在有效期内") Boolean dateStatus){
       return iSmUserCouponService.list(RequestHolder.user().getId(),shopId,useStatus,dateStatus);
    }

    @ApiOperation("领取优惠券")
    @PostMapping("/current/{couponId}")
    public SmUserCoupon userPull(@PathVariable Long couponId){
        return iSmUserCouponService.userPull(couponId,RequestHolder.user().getId());
    }
    /**
     * 商家发放优惠券
     */

    @ApiOperation("商家发放优惠券")
    @PostMapping("/shop/current/{couponId}")
    public SmUserCoupon shopPush(@PathVariable Long couponId,@RequestParam Long userId){
        return iSmUserCouponService.shopPush(couponId,RequestHolder.user().getShopId(),userId);
    }





}
