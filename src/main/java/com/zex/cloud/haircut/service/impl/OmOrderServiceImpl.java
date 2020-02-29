package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zex.cloud.haircut.apis.WxApi;
import com.zex.cloud.haircut.config.MqConfig;
import com.zex.cloud.haircut.config.WxProperties;
import com.zex.cloud.haircut.entity.OmComment;
import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.entity.OmShopOrder;
import com.zex.cloud.haircut.enums.OrderStatus;
import com.zex.cloud.haircut.enums.OrderType;
import com.zex.cloud.haircut.enums.PayChannelType;
import com.zex.cloud.haircut.enums.ShopOrderStatus;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.exception.NotSupportException;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.mapper.OmOrderMapper;
import com.zex.cloud.haircut.message.OrderCreatedMessage;
import com.zex.cloud.haircut.params.OmCommentOrderParam;
import com.zex.cloud.haircut.params.OmOrderParam;
import com.zex.cloud.haircut.response.*;
import com.zex.cloud.haircut.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.util.*;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
@Service
public class OmOrderServiceImpl extends ServiceImpl<OmOrderMapper, OmOrder> implements IOmOrderService {

    @Autowired
    private IOmShopOrderService iOmShopOrderService;
    @Autowired
    private IdentifierGenerator identifierGenerator;
    @Autowired
    private ISmShopGrouponService iSmShopGrouponService;
    @Autowired
    private IOmUserGrouponService iOmUserGrouponService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${host-url}")
    private String hostUrl;

    @Autowired
    private Retrofit wxRetrofit;
    @Autowired
    private IOmFlowerService iOmFlowerService;

    @Autowired
    private IOmUserTransactionService iOmUserTransactionService;

    @Autowired
    private IOmRefundOrderService iOmRefundOrderService;

    @Override
    @Transactional
    public OmOrder createOrder(OmOrderParam param, String ip, Long userId) throws JsonProcessingException {
        Long generateId = (Long) identifierGenerator.nextId(null);
        String body = null;
        if (param.getOrderType() == OrderType.SHOP_SERVICE) {
            body = iOmShopOrderService.validOrderAndCreate(param, userId, generateId);
        } else if (param.getOrderType() == OrderType.SHOP_GROUPON) {
            body = iSmShopGrouponService.validOrder(param, userId, generateId);
        }
        OmOrder omOrder = new OmOrder();
        omOrder.setId(generateId);
        omOrder.setAmount(param.getAmount());
        omOrder.setBody(body);
        omOrder.setExpireAt(LocalDateTime.now().plusDays(1));
        omOrder.setStatus(OrderStatus.PENDING);
        omOrder.setIpAddress(ip);
        omOrder.setUserId(userId);
        save(omOrder);
        OrderCreatedMessage message = new OrderCreatedMessage();
        message.setOrderId(generateId);
        rabbitTemplate.convertAndSend(MqConfig.DElAY_PAY_EXCHANGE, MqConfig.DElAY_PAY_QUEUE, message, processor -> {
            processor.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            processor.getMessageProperties().setDelay(24 * 3600 * 1000);
            return processor;
        });
        return omOrder;
    }

    @Override
    public void cancelOrder(Long id, Long userId) {
        OmOrder omOrder = getById(id);
        if (omOrder.getStatus() != OrderStatus.PENDING) {
            throw new ForbiddenException("订单只有在等待付款状态才能取消");
        }
        if (!omOrder.getUserId().equals(userId)) {
            throw new ForbiddenException();
        }
        if (omOrder.getOrderType() == OrderType.SHOP_SERVICE) {
            iOmShopOrderService.cancelOrder(id);
        }

    }

    @Override
    public WxJsResponse createPayment(Long id, String ip) {
        OmOrder omOrder = getById(id);
        if (omOrder == null || omOrder.getStatus() != OrderStatus.PENDING) {
            throw new ForbiddenException("订单不存在, 或不在PENDING状态");
        }
        WxUnifiedorderRequest wxRequest = new WxUnifiedorderRequest();
        wxRequest.setAppid(WxProperties.APP_ID);
        wxRequest.setMchId(WxProperties.PARTNER_ID);
        wxRequest.setNonceStr(RandomUtil.getRandomStr());
        wxRequest.setNotifyUrl(hostUrl + "/api/v1/hooks/wx/paid");
        wxRequest.setSpbillCreateIp(ip);
        wxRequest.setTradeType("JSAPI");
        wxRequest.setTimeStart(DateTimeUtils.format(omOrder.getExpireAt(), "yyyyMMddHHmmss"));
        wxRequest.setTimeExpire(DateTimeUtils.format(omOrder.getExpireAt(), "yyyyMMddHHmmss"));
        wxRequest.setBody(omOrder.getSubject());
        wxRequest.setOutTradeNo(String.valueOf(omOrder.getId()));
        wxRequest.setTotalFee(DecimalUtils.multiply(omOrder.getAmount(), new BigDecimal("10")).intValue());
        wxRequest.setDetail(omOrder.getBody());
        // TODO: 2020/2/25 获取OpenId
        wxRequest.setOpenid("");
//        wxRequest.setAttach(transactionOrder.getBody());
        wxRequest.setSign(WxPaySignature.sign(MapUtil.buildMap(wxRequest), WxProperties.SIGN_KEY));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), XmlUtil.toString(wxRequest));
        Call<WxCreateOrderResponse> call = wxRetrofit.create(WxApi.class).unifiedorder(requestBody);
        try {
            Response<WxCreateOrderResponse> response = call.execute();
            if (response.isSuccessful()) {
                WxCreateOrderResponse body = response.body();
                assert body != null;
                if (StringUtils.equals(body.getReturnCode(), WxProperties.SUCCESS)) {
                    throw new ServerException("调用微信失败 returnCode != SUCCESS, returnMsg = " + body.getReturnCode());
                }
                if (StringUtils.equals(body.getResultCode(), WxProperties.SUCCESS)) {
                    throw new ServerException("调用微信失败 resultCode != SUCCESS, err_code = " + body.getErrCode() + "   err_code_des =" + body.getErrCodeDes());
                }
                WxJsResponse wxJsResponse = new WxJsResponse();
                wxJsResponse.setAppId(WxProperties.APP_ID);
                wxJsResponse.setNonceStr(RandomUtil.getRandomStr());
                wxJsResponse.setPackageValue("prepay_id=" + body.getPrepayId());
                wxJsResponse.setTimeStamp(String.valueOf(System.currentTimeMillis() / 1000));
                wxJsResponse.setSignType("MD5");
                wxJsResponse.setPaySign(WxPaySignature.sign(MapUtil.buildMap(wxJsResponse), WxProperties.SIGN_KEY));
                return wxJsResponse;
            } else {
                throw new ServerException("调用微信失败 response code = " + response.code());
            }
        } catch (IOException e) {
            throw new ServerException("调用微信IO异常");
        }
    }

    @Override
    @Transactional
    public void onPayHook(String orderId, BigDecimal amount, String tradeNo) {
        OmOrder omOrder = getById(orderId);
        if (omOrder == null) {
            throw new NotFoundException();
        }

        if (DecimalUtils.ne(omOrder.getAmount(), amount)) {
            throw new ServerException("金额不符");
        }
        if (omOrder.getStatus() != OrderStatus.PENDING) {
            throw new ServerException("该订单不在待支付状态");
        }
        if (omOrder.getOrderType() == OrderType.SHOP_SERVICE) {
            iOmShopOrderService.onPayHook(Long.valueOf(orderId));
        } else if (omOrder.getOrderType() == OrderType.SHOP_GROUPON) {
            iOmUserGrouponService.onPayHook(omOrder);
        } else if (omOrder.getOrderType() == OrderType.USER_FLOWER) {
            iOmFlowerService.onPayHook(omOrder);
        } else if (omOrder.getOrderType() == OrderType.USER_RECHARGE) {
            iOmUserTransactionService.onPayHook(omOrder);
        }
        omOrder.setStatus(OrderStatus.PAID);
        omOrder.setPayAt(LocalDateTime.now());
        omOrder.setChannelType(PayChannelType.WEI_XIN);
        omOrder.setThirdPartyId(tradeNo);
        updateById(omOrder);
    }

    @Override
    public void walletPayment(Long id, String transactionPassword, Long userId) {
        // TODO: 2020/2/26 效验支付密码
        OmOrder omOrder = getById(id);
        if (omOrder == null) {
            throw new NotFoundException("订单不存在");
        }
        if (omOrder.getStatus() != OrderStatus.PENDING) {
            throw new ServerException("该订单不在待支付状态");
        }
        iOmUserTransactionService.payOrder(id, omOrder.getAmount(), userId);
    }

    @Override
    @Transactional
    public void refund(Long id, Long userId) {

        OmOrder omOrder = getById(id);
        if (omOrder == null) {
            throw new NotFoundException("订单不存在");
        }
        if (!omOrder.getUserId().equals(userId)){
            throw new ForbiddenException();
        }
        if (omOrder.getStatus() != OrderStatus.PAID) {
            throw new ServerException("该订单不在已支付状态");
        }
        BigDecimal refundAmount = omOrder.getAmount();
        if (omOrder.getOrderType() == OrderType.SHOP_SERVICE) {
            iOmShopOrderService.refund(id,userId);
        } else if (omOrder.getOrderType() == OrderType.SHOP_GROUPON) {
            refundAmount =  iOmUserGrouponService.refund(id,userId);
        }else {
            throw new NotSupportException("暂不支持该类型的订单退款");
        }

        //创建退款订单
        Long refundId = iOmRefundOrderService.create(omOrder.getId(), userId, refundAmount, "用户退款", omOrder.getChannelType());
        if (omOrder.getChannelType() == PayChannelType.WEI_XIN){//退款到微信
            WxRefundRequest wxRequest = new WxRefundRequest();
            wxRequest.setAppid(WxProperties.APP_ID);
            wxRequest.setMchId(WxProperties.PARTNER_ID);
            wxRequest.setNonceStr(RandomUtil.getRandomStr());
            wxRequest.setNotifyUrl(hostUrl + "/api/v1/hooks/wx/refund");
            wxRequest.setNonceStr(RandomUtil.getRandomStr());
            wxRequest.setOutTradeNo(String.valueOf(omOrder.getId())); //系统订单号
            wxRequest.setOutRefundNo(String.valueOf(refundId));  //退款单号
            wxRequest.setTotalFee(String.valueOf(DecimalUtils.multiply(omOrder.getAmount(), new BigDecimal("10")).intValue()));     //订单金额
            wxRequest.setRefundFee(String.valueOf(DecimalUtils.multiply(refundAmount, new BigDecimal("10")).intValue()));   //退款金额
            wxRequest.setRefundDesc("用户退款");
            wxRequest.setSign(WxPaySignature.sign(MapUtil.buildMap(wxRequest), WxProperties.SIGN_KEY));

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), XmlUtil.toString(wxRequest));
            Call<WxRefundResponse> call = wxRetrofit.create(WxApi.class).refund(requestBody);

            try {
                Response<WxRefundResponse> response = call.execute();
                if (response.isSuccessful()) {
                    WxRefundResponse body = response.body();
                    assert body != null;
                    if (StringUtils.equals(body.getReturnCode(), WxProperties.SUCCESS)) {
                        throw new ServerException("调用微信失败 returnCode != SUCCESS, returnMsg = " + body.getReturnCode());
                    }
                    if (StringUtils.equals(body.getResultCode(), WxProperties.SUCCESS)) {
                        throw new ServerException("调用微信失败 resultCode != SUCCESS, err_code = " + body.getErrCode() + "   err_code_des =" + body.getErrCodeDes());
                    }
                } else {
                    throw new ServerException("调用微信失败 response code = " + response.code());
                }
            } catch (IOException e) {
                throw new ServerException("调用微信IO异常");
            }

        }else { //直接退款到钱包
            iOmUserTransactionService.refund(omOrder.getId(),userId,refundAmount,refundId);
        }

        omOrder.setStatus(OrderStatus.REFUND);
        updateById(omOrder);
    }



}
