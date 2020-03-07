package com.zex.cloud.haircut.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.OmShopOrder;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.enums.ShopOrderStatus;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.service.IOmShopOrderService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/api/v1/shop/orders")
@Api(tags = "店铺服务订单")
public class OmShopOrderController {
    @Autowired
    private IOmShopOrderService iOmShopOrderService;

    @GetMapping
    public IPage<OmShopOrder> shopOrder(Pageable pageable, String keywords, Long shopId, Long stylistId, Long userId, ShopOrderStatus status, GenderType genderType, LocalDateTime startAt, LocalDateTime endAt) {
        return iOmShopOrderService.page(pageable.convert(), new LambdaQueryWrapper<OmShopOrder>()
                .eq(shopId != null, OmShopOrder::getShopId, shopId)
                .eq(stylistId != null, OmShopOrder::getStylistId, stylistId)
                .eq(userId != null, OmShopOrder::getUserId, userId)
                .eq(status != null, OmShopOrder::getStatus, status)
                .eq(genderType != null, OmShopOrder::getGenderType, genderType)
                .ge(startAt != null, OmShopOrder::getCreateAt, startAt)
                .le(endAt != null, OmShopOrder::getCreateAt, endAt)
                .and(StringUtils.isNotBlank(keywords), i ->
                        i.like(OmShopOrder::getOrderId, keywords)
                                .or()
                                .like(OmShopOrder::getId, keywords))
        .orderByDesc(OmShopOrder::getCreateAt));
    }


}
