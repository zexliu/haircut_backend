package com.zex.cloud.haircut.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmAgent;
import com.zex.cloud.haircut.enums.ClientType;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.enums.ShopOrderStatus;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IOmShopOrderService;
import com.zex.cloud.haircut.service.ISmAgentService;
import com.zex.cloud.haircut.vo.OmShopOrderDetailVO;
import com.zex.cloud.haircut.vo.OmShopOrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public IPage<OmShopOrderVO> shopOrder(Pageable pageable, String keywords, Long shopId, Long stylistId, Long userId, ShopOrderStatus status, GenderType genderType, LocalDateTime startAt, LocalDateTime endAt,Boolean useStatus,Boolean payStatus,Boolean isToday,Boolean isAfterToday,Integer provinceCode ,Integer cityCode) {
        return iOmShopOrderService.shopOrderVO(pageable.convert(),keywords,shopId,stylistId,userId,status,genderType,startAt,endAt,useStatus,payStatus,isToday,isAfterToday,provinceCode,cityCode);
    }

    @GetMapping("/current")
    @ApiOperation("店铺查询订单")
    public IPage<OmShopOrderVO> currentShopOrder(Pageable pageable, String keywords, Long shopId, Long stylistId, Long userId, ShopOrderStatus status, GenderType genderType, LocalDateTime startAt, LocalDateTime endAt,Boolean useStatus,Boolean payStatus) {
        if (RequestHolder.user().getClientType() == ClientType.SHOP_CLIENT){
            shopId = RequestHolder.user().getShopId();
        }else {
            userId = RequestHolder.user().getId();
        }
        return shopOrder(pageable, keywords, shopId, stylistId, userId, status, genderType, startAt, endAt,useStatus,payStatus,null,null,null,null);
    }


    @GetMapping("/{id}")
    @ApiOperation("查询订单详情")
    public OmShopOrderDetailVO getOrderById(@PathVariable Long id){
       return iOmShopOrderService.getDetailById(id);
    }

}
