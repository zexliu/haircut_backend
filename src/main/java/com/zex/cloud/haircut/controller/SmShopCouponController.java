package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmShopCoupon;
import com.zex.cloud.haircut.enums.CouponPublishType;
import com.zex.cloud.haircut.enums.CouponType;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SmShopCouponParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmShopCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-21
 */
@RestController
@Api(tags = "店铺优惠券管理")
@RequestMapping("/api/v1/shop/coupons")
public class SmShopCouponController {

    @Autowired
    private ISmShopCouponService iSmShopCouponService;

    @GetMapping("/current")
    @ApiOperation("获取当前商铺的优惠券列表")
    public IPage<SmShopCoupon> currentPage(Pageable pageable, String keywords, CouponPublishType publishType, Boolean publishStatus, CouponType couponType) {
        return iSmShopCouponService.page(pageable.convert(),
                new LambdaQueryWrapper<SmShopCoupon>()
                        // TODO: 2020/2/21 移除条件
                        .eq(RequestHolder.user().getShopId() != null,SmShopCoupon::getShopId, RequestHolder.user().getShopId())
                        .like(StringUtils.isNotBlank(keywords), SmShopCoupon::getName, keywords)
                        .eq(publishStatus != null, SmShopCoupon::getPublishStatus, publishStatus)
                        .eq(publishType != null, SmShopCoupon::getPublishType, publishType)
                        .eq(couponType != null, SmShopCoupon::getCouponType, couponType));

    }

    @GetMapping
    @ApiOperation("获取优惠券列表")
    public IPage<SmShopCoupon> page(Pageable pageable, Long shopId, String keywords, CouponPublishType publishType, Boolean publishStatus, CouponType couponType, Boolean pullDate) {
        return iSmShopCouponService.page(pageable.convert(),
                new LambdaQueryWrapper<SmShopCoupon>()
                        .like(StringUtils.isNotBlank(keywords), SmShopCoupon::getName, keywords)
                        .eq(shopId != null, SmShopCoupon::getShopId, shopId)
                        .eq(publishStatus != null, SmShopCoupon::getPublishStatus, publishStatus)
                        .eq(publishType != null, SmShopCoupon::getPublishType, publishType)
                        .le(pullDate != null, SmShopCoupon::getPullStartAt, LocalDateTime.now())
                        .ge(pullDate != null, SmShopCoupon::getPullEndAt, LocalDateTime.now())
                        .eq(couponType != null, SmShopCoupon::getCouponType,couponType));

    }

    @PostMapping
    public SmShopCoupon create(@RequestBody @Valid SmShopCouponParam param) {
        return iSmShopCouponService.save(RequestHolder.user().getId(), param);
    }

    @PutMapping("/{id}/publishStatus")
    public String updatePublishStatus(@PathVariable Long id, Boolean publishStatus) {
        iSmShopCouponService.updateStatus(id, RequestHolder.user().getShopId(), publishStatus);
        return SimpleResp.SUCCESS;
    }

}
