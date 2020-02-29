package com.zex.cloud.haircut.controller;

import com.zex.cloud.haircut.config.WxProperties;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.response.WxCreateOrderHookResponse;
import com.zex.cloud.haircut.response.WxRefundResponse;
import com.zex.cloud.haircut.service.IOmOrderService;
import com.zex.cloud.haircut.service.IOmRefundOrderService;
import com.zex.cloud.haircut.util.AESUtil;
import com.zex.cloud.haircut.util.DecimalUtils;
import com.zex.cloud.haircut.util.WxPaySignature;
import com.zex.cloud.haircut.util.XmlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/hooks")
@Slf4j
@Api(tags = "钩子接口")
public class HookController {

    @Autowired
    private IOmOrderService iOmOrderService;

    @Autowired
    private IOmRefundOrderService iOmRefundOrderService;

    @PostMapping("/wx/paid")
    @ApiOperation("微信支付成功通知")
    @ResponseBody
    public String wxPaid(@RequestBody String notice) {
        if (!WxPaySignature.verify(XmlUtil.toMap(notice), WxProperties.SIGN_KEY)) {
            log.error("【微信支付异步通知】签名验证失败, response={}", notice);
            throw new ServerException("【微信支付异步通知】 签名验证失败");
        }
        //xml解析为对象
        WxCreateOrderHookResponse createOrderHookResponse = XmlUtil.toObject(notice, WxCreateOrderHookResponse.class);
        if (createOrderHookResponse == null) {
            throw new RuntimeException("【微信支付异步通知】XML解析失败");
        }
        if (!createOrderHookResponse.getReturnCode().equals(WxProperties.SUCCESS)) {
            throw new RuntimeException("【微信支付异步通知】发起支付, returnCode != SUCCESS, returnMsg = "
                    + createOrderHookResponse.getReturnMsg());
        }

        //获取订单号
        String orderId = createOrderHookResponse.getOutTradeNo();
        //交易金额
        BigDecimal amount = DecimalUtils.divide(new BigDecimal(createOrderHookResponse.getTotalFee()),new BigDecimal("100"));

        //交易流水
        String tradeNo = createOrderHookResponse.getTransactionId();

        iOmOrderService.onPayHook(orderId, amount, tradeNo);

        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }


    @PostMapping("/wx/refund")
    @ApiOperation("微信退款成功通知")
    @ResponseBody
    public String refund(@RequestBody String notice) {
        WxRefundResponse wxRefundResponse = XmlUtil.toObject(notice, WxRefundResponse.class);
        if (wxRefundResponse == null) {
            throw new RuntimeException("【微信退款异步通知】XML解析失败");
        }
        if (!wxRefundResponse.getReturnCode().equals(WxProperties.SUCCESS)) {
            throw new RuntimeException("【微信退款异步通知】发起支付, returnCode != SUCCESS, returnMsg = "
                    + wxRefundResponse.getReturnMsg());
        }


        try {
            String s = AESUtil.decryptData(wxRefundResponse.getReqInfo(), WxProperties.SIGN_KEY);
            wxRefundResponse = XmlUtil.toObject(s, WxRefundResponse.class);
            log.info("微信 wxRefundResponse {}", wxRefundResponse);
            if (wxRefundResponse == null) {
                throw new RuntimeException("【微信退款异步通知】XML解析失败");
            }
            String refundId = wxRefundResponse.getOutRefundNo();
            //交易金额
            BigDecimal amount = DecimalUtils.divide(new BigDecimal(wxRefundResponse.getRefundFee()),new BigDecimal("100"));
            //交易流水
            String tradeNo = wxRefundResponse.getRefundId();
            iOmRefundOrderService.onRefundHook(Long.valueOf(refundId),amount ,tradeNo);
            return "<xml>\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
        } catch (Exception e) {
            throw new RuntimeException("【微信退款异步通知】XML解析失败");
        }

    }

}
