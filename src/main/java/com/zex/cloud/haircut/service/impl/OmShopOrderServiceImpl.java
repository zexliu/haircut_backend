package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zex.cloud.haircut.config.MqConfig;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.OmShopOrder;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.enums.OrderType;
import com.zex.cloud.haircut.enums.ShopOrderStatus;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.exception.ParameterException;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.mapper.OmShopOrderMapper;
import com.zex.cloud.haircut.message.OrderUsedMessage;
import com.zex.cloud.haircut.params.OmOrderParam;
import com.zex.cloud.haircut.params.OmShopOrderBodyParam;
import com.zex.cloud.haircut.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.util.DecimalUtils;
import com.zex.cloud.haircut.vo.OmShopOrderDetailVO;
import com.zex.cloud.haircut.vo.OmShopOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
@Service
@Slf4j
public class OmShopOrderServiceImpl extends ServiceImpl<OmShopOrderMapper, OmShopOrder> implements IOmShopOrderService {

    @Autowired
    private IHmStylistServiceRelationService iHmStylistServiceRelationService;

    @Autowired
    private ISmHalfTimeService iSmHalfTimeService;

    @Autowired
    private ISmShopDiscountService iSmShopDiscountService;

    @Autowired
    private ISmUserCouponService iSmUserCouponService;

    @Autowired
    private IOmCommentScoreService iOmCommentScoreService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ILedgerAccountService iLedgerAccountService;

    @Override
    public String validOrderAndCreate(OmOrderParam param, Long userId, Long orderId) throws JsonProcessingException {
        OmShopOrderBodyParam body = param.getShopOrderBody();

        if (body == null) {
            throw new ParameterException("shopOrderBody不能为空");
        }
        if (body.getAppointmentAt() == null || body.getAppointmentAt().isBefore(LocalDateTime.now())) {
            body.setAppointmentAt(LocalDateTime.now());
        }
        BigDecimal totalOriginPrice = new BigDecimal("0");
        BigDecimal totalRealPrice = new BigDecimal("0");
        //便利项目
        //获取实际价格
        BigDecimal originPrice = iHmStylistServiceRelationService.getPriceByServiceIdStylistIdAndSex(body.getServiceId(), body.getStylistId(), body.getGenderType());
        BigDecimal realPrice = originPrice;
        if (DecimalUtils.ne(originPrice, body.getOriginalAmount())) {
            throw new ParameterException("原价不符 serviceId =" + body.getServiceId());
        }

        if (body.getIsHalf() != null && body.getIsHalf()) {
            //效验是否在半价时间内
            boolean valid = iSmHalfTimeService.valid(body.getShopId(), body.getAppointmentAt());
            if (!valid) {
                throw new ServerException("不在半价时间内");
            }
            realPrice = DecimalUtils.divide(realPrice, new BigDecimal("2"));
        }else {
            //获取优惠金额
            if (body.getDiscountId() != null) {
                BigDecimal discount = iSmShopDiscountService.getDiscountByServiceIdAndShopId(body.getServiceId(), body.getShopId());
                realPrice = DecimalUtils.divide(DecimalUtils.multiply(realPrice, discount), new BigDecimal("10"));
            }
        }
        if (DecimalUtils.ne(realPrice, body.getRealAmount())) {
            throw new ParameterException("实际金额不符 serviceId =" + body);
        }

        //获取总原价
        totalOriginPrice = DecimalUtils.add(totalOriginPrice, originPrice);
        //获取总实际金额
        totalRealPrice = DecimalUtils.add(totalRealPrice, realPrice);

//        for (OmOrderServiceParam service : body.getServices()) {
//            //获取实际价格
//            BigDecimal originPrice = iHmStylistServiceRelationService.getPriceByServiceIdStylistIdAndSex(service.getServiceId(), body.getStylistId(), body.getGenderType());
//            BigDecimal realPrice = originPrice;
//            if (DecimalUtils.ne(originPrice, service.getOriginalAmount())) {
//                throw new ParameterException("原价不符 serviceId =" + service);
//            }
//
//            //获取优惠金额
//            if (service.getDiscountId() != null) {
//                BigDecimal discount = iSmShopDiscountService.getDiscountByServiceIdAndShopId(service.getServiceId(), body.getShopId());
//                realPrice = DecimalUtils.multiply(realPrice, discount);
//            }
//            if (service.getIsHalf() != null && service.getIsHalf()) {
//                //效验是否在半价时间内
//                boolean valid = iSmHalfTimeService.valid(body.getShopId(), body.getAppointmentAt());
//                if (!valid){
//                    throw new ServerException("不在半价时间内");
//                }
//                realPrice = DecimalUtils.divide(realPrice, new BigDecimal("2"));
//            }
//            if (DecimalUtils.ne(realPrice, service.getRealAmount())) {
//                throw new ParameterException("实际金额不符 serviceId =" + service);
//            }
//
//            //获取总原价
//            totalOriginPrice = DecimalUtils.add(totalOriginPrice, originPrice);
//            //获取总实际金额
//            totalRealPrice = DecimalUtils.add(totalRealPrice, realPrice);
//        }
        //优惠券
        if (body.getCouponId() != null) {
            //使用优惠券并验证优惠金额
            BigDecimal couponAmount = iSmUserCouponService.useCoupon(body.getCouponId(), userId, orderId, body.getShopId(), totalRealPrice);
            totalRealPrice = DecimalUtils.subtract(totalRealPrice, couponAmount);
        }

        if (DecimalUtils.ne(totalRealPrice, param.getAmount())) {
            throw new ParameterException("合计金额不符,实际金额为:" + totalRealPrice);
        }

        OmShopOrder omShopOrder = new OmShopOrder();
        omShopOrder.setAppointmentAt(body.getAppointmentAt());
        //先设置有效期为一年
        omShopOrder.setExpireAt(LocalDateTime.now().plusYears(1));
        omShopOrder.setOrderId(orderId);
        omShopOrder.setRealAmount(totalRealPrice);
        omShopOrder.setShopId(body.getShopId());
        omShopOrder.setStatus(ShopOrderStatus.PENDING_PAY);
        omShopOrder.setStylistId(body.getStylistId());
        omShopOrder.setSubject(param.getSubject());
        omShopOrder.setTotalAmount(totalOriginPrice);
        omShopOrder.setUserId(userId);
        omShopOrder.setGenderType(body.getGenderType());
        omShopOrder.setCreateAt(LocalDateTime.now());
//        OmShopOrderBodyParam shopOrderBody = new OmShopOrderBodyParam();
//        shopOrderBody.setCouponId(body.getCouponId());
//        shopOrderBody.setServices(body.getServices());
        String jsonBody = objectMapper.writeValueAsString(body);
        omShopOrder.setBody(jsonBody);
        save(omShopOrder);

        return objectMapper.writeValueAsString(omShopOrder);

    }

    @Override
    public void cancelOrder(Long id) {
        OmShopOrder omShopOrder = getByOrderId(id);
        if (omShopOrder == null || omShopOrder.getStatus() != ShopOrderStatus.PENDING_PAY) {
            log.warn("shop order not found , orderId = {}", id);
            return;
        }
        omShopOrder.setStatus(ShopOrderStatus.CANCEL);
        updateById(omShopOrder);
    }

    @Override
    public void onPayHook(Long orderId) {
        OmShopOrder omShopOrder = getByOrderId(orderId);
        if (omShopOrder == null || omShopOrder.getStatus() != ShopOrderStatus.PENDING_PAY) {
            throw new ServerException();
        }
        omShopOrder.setStatus(ShopOrderStatus.PENDING_USE);
        updateById(omShopOrder);
    }

    @Override
    public void refund(Long id, Long userId) {
        OmShopOrder omShopOrder = getByOrderId(id);
        if (omShopOrder.getStatus() != ShopOrderStatus.PENDING_USE) {
            throw new ForbiddenException("该订单不在待使用状态");
        }
        if (!omShopOrder.getUserId().equals(userId)) {
            throw new ForbiddenException();
        }
        omShopOrder.setStatus(ShopOrderStatus.CANCEL);
        updateById(omShopOrder);
    }

    @Override
    public Long validUseStatus(Long id, Long userId) {
        OmShopOrder omShopOrder = getById(id);
        if (omShopOrder == null) {
            throw new NotFoundException();
        }
        if (omShopOrder.getStatus() != ShopOrderStatus.PENDING_USE) {
            throw new ForbiddenException("订单不在可用状态");
        }
        if (!omShopOrder.getUserId().equals(userId)) {
            throw new ForbiddenException();
        }
        return omShopOrder.getShopId();
    }

    @Override
    @Transactional
    public void use(Long id, Long shopId) {
        OmShopOrder omShopOrder = getById(id);
        if (omShopOrder == null) {
            throw new NotFoundException();
        }
        if (omShopOrder.getStatus() != ShopOrderStatus.PENDING_USE) {
            throw new ForbiddenException("订单不在可用状态");
        }
        if (!omShopOrder.getShopId().equals(shopId)) {
            throw new ForbiddenException();
        }
        //设置待评价状态
        omShopOrder.setStatus(ShopOrderStatus.PENDING_EVALUATION);
        omShopOrder.setUseAt(LocalDateTime.now());
        // 修改订单状态为待评价
        iLedgerAccountService.account(omShopOrder.getId(), omShopOrder.getRealAmount(), omShopOrder.getUserId(), omShopOrder.getShopId(), OrderType.SHOP_SERVICE);
        updateById(omShopOrder);

        OrderUsedMessage message = new OrderUsedMessage();
        message.setUserId(omShopOrder.getUserId());
        message.setOrderId(id);
        rabbitTemplate.convertAndSend(MqConfig.DElAY_COMMENT_EXCHANGE, MqConfig.DElAY_COMMENT_QUEUE, message, processor -> {
            processor.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            processor.getMessageProperties().setDelay(72 * 3600 * 1000);
            return processor;
        });
        //发布mq延时消息
    }

    @Override
    public OmShopOrder comment(Long shopOrderId, Long userId, Boolean auto) {
        OmShopOrder shopOrder = getById(shopOrderId);
        if (shopOrder == null) {
            throw new NotFoundException();
        }
        if (!shopOrder.getUserId().equals(userId)) {
            throw new ForbiddenException();
        }
        if (shopOrder.getStatus() != ShopOrderStatus.PENDING_EVALUATION) {
            if (auto){
                return null;
            }else {
                throw new ForbiddenException("该订单不在待评价状态");
            }
        }
        shopOrder.setStatus(ShopOrderStatus.FINISH);
        updateById(shopOrder);
        return shopOrder;
    }

    @Override
    public IPage<OmShopOrderVO> shopOrderVO(IPage<OmShopOrderVO> page, String keywords, Long shopId, Long stylistId, Long userId, ShopOrderStatus status, GenderType genderType, LocalDateTime startAt, LocalDateTime endAt, Boolean useStatus, Boolean payStatus, Boolean isToday, Boolean isAfterToday, Integer provinceCode, Integer cityCode) {
        IPage<OmShopOrderVO> omShopOrderVOIPage = baseMapper.shopOrderVO(page, keywords, shopId, stylistId, userId, status, genderType, startAt, endAt, useStatus, payStatus, isToday, isAfterToday, provinceCode, cityCode);
        omShopOrderVOIPage.convert(omShopOrderVO -> {
            //手艺值
            int commentCount = iOmCommentScoreService.stylistGoodCommentCount(omShopOrderVO.getStylistId());
            int orderCount = stylistOrderCount(omShopOrderVO.getStylistId());
            omShopOrderVO.setSkillPraise(commentCount + orderCount);
            return omShopOrderVO;
        });
        return omShopOrderVOIPage;
    }

    @Override
    public int getWaitCount(Long shopId, Long stylistId, LocalDateTime now) {
        return count(new LambdaQueryWrapper<OmShopOrder>()
                .eq(shopId != null,OmShopOrder::getShopId,shopId)
                .eq(stylistId != null, OmShopOrder::getStylistId, stylistId)
                .eq(OmShopOrder::getStatus, ShopOrderStatus.PENDING_USE)
                .ge(OmShopOrder::getAppointmentAt, now)
                .le(OmShopOrder::getAppointmentAt, now.plusHours(6)));
    }


    @Override
    public int stylistOrderCount(Long id) {
        return count(new LambdaQueryWrapper<OmShopOrder>().eq(OmShopOrder::getStylistId, id));
    }

    @Override
    public BigDecimal income(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode) {
        BigDecimal income = baseMapper.income(startAt, endAt, provinceCode, cityCode);
        if (income == null) {
            return new BigDecimal("0");
        }
        return income;
    }

    @Override
    public List<BrokenLinePoint> brokenLinesAgent(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode) {
        return baseMapper.brokenLinesAgent(startAt, endAt, provinceCode, cityCode);
    }

    @Override
    public OmShopOrderDetailVO getDetailById(Long id) {
        return baseMapper.getDetailById(id);
    }


    private OmShopOrder getByOrderId(Long id) {
        return getOne(new LambdaQueryWrapper<OmShopOrder>().eq(OmShopOrder::getOrderId, id));
    }
}
