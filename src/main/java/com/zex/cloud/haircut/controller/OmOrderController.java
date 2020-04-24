package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zex.cloud.haircut.entity.OmComment;
import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.enums.OrderStatus;
import com.zex.cloud.haircut.enums.OrderType;
import com.zex.cloud.haircut.enums.PayChannelType;
import com.zex.cloud.haircut.params.OmCommentOrderParam;
import com.zex.cloud.haircut.params.OmOrderParam;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.response.WxJsResponse;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IOmOrderService;
import com.zex.cloud.haircut.util.NetWorkUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/api/v1/orders")
@Api(tags = "订单操作接口")
public class  OmOrderController {
    @Autowired
    private IOmOrderService iOmOrderService;

    @ApiOperation("查询订单列表")
    @GetMapping
    public IPage<OmOrder> orderList(Pageable pageable, OrderType orderType,
                                    OrderStatus orderStatus, Long userId,
                                    PayChannelType channelType, String keywords,
                                    LocalDateTime startAt, LocalDateTime endAt) {
        return iOmOrderService.page(pageable.convert(), new LambdaQueryWrapper<OmOrder>()
                .eq(orderType != null, OmOrder::getOrderType, orderType)
                .eq(orderStatus != null, OmOrder::getStatus, orderStatus)
                .eq(userId != null, OmOrder::getUserId, userId)
                .eq(channelType != null, OmOrder::getChannelType, channelType)
                .ge(startAt != null, OmOrder::getCreateAt, startAt)
                .le(endAt != null, OmOrder::getCreateAt, endAt)
                .and(StringUtils.isNotBlank(keywords), i ->
                        i.like(OmOrder::getId, keywords)
                                .or()
                                .like(OmOrder::getThirdPartyId, keywords))
                .orderByDesc(OmOrder::getCreateAt)
        );
    }

    @ApiOperation("创建订单")
    @PostMapping
    public OmOrder createOrder(@Valid @RequestBody OmOrderParam param, HttpServletRequest request) throws JsonProcessingException {
        String ip = NetWorkUtils.getRemoteHost(request);
        return iOmOrderService.createOrder(param, ip,  RequestHolder.user());
    }

    @ApiOperation("取消订单")
    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Long id) {
        iOmOrderService.cancelOrder(id, RequestHolder.user().getId());
        return SimpleResp.SUCCESS;
    }

    @ApiOperation("发起微信支付请求")
    @PostMapping("/payment/wx/{id}")
    public WxJsResponse createPayment(@PathVariable Long id, HttpServletRequest request) {
        return iOmOrderService.createPayment(id, NetWorkUtils.getRemoteHost(request));
    }

    @ApiOperation("使用钱包支付")
    @PostMapping("/payment/wallet/{id}")
    public String walletPayment(@PathVariable Long id, @RequestParam String transactionPassword) {
        iOmOrderService.walletPayment(id, transactionPassword, RequestHolder.user().getId());
        return SimpleResp.SUCCESS;
    }

    @ApiOperation("申请退款")
    @PostMapping("/refund/{id}")
    public String refund(@PathVariable Long id,String description) {
        iOmOrderService.refund(id, RequestHolder.user().getId(),description);
        return SimpleResp.SUCCESS;
    }


}
