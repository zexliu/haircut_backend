package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zex.cloud.haircut.dto.OrderGrouponDTO;
import com.zex.cloud.haircut.entity.SmShopGroupon;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.exception.ParameterException;
import com.zex.cloud.haircut.mapper.SmShopGrouponMapper;
import com.zex.cloud.haircut.params.OmOrderParam;
import com.zex.cloud.haircut.params.OmShopGrouponBodyParam;
import com.zex.cloud.haircut.params.SmShopGrouponParam;
import com.zex.cloud.haircut.service.IHmStylistServiceRelationService;
import com.zex.cloud.haircut.service.ISmShopGrouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.service.ISmUserCouponService;
import com.zex.cloud.haircut.util.DecimalUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
@Service
public class SmShopGrouponServiceImpl extends ServiceImpl<SmShopGrouponMapper, SmShopGroupon> implements ISmShopGrouponService {

    @Autowired
    private IHmStylistServiceRelationService iHmStylistServiceRelationService;

    @Autowired
    private ISmUserCouponService iSmUserCouponService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public SmShopGroupon create(SmShopGrouponParam param, Long shopId) {
        SmShopGroupon smShopGroupon = new SmShopGroupon();
        BeanUtils.copyProperties(param, smShopGroupon);
        smShopGroupon.setShopId(shopId);
        save(smShopGroupon);
        return smShopGroupon;
    }

    @Override
    public SmShopGroupon update(Long id, SmShopGrouponParam param, Long shopId) {
        SmShopGroupon smShopGroupon = getById(id);
        if (smShopGroupon == null || !smShopGroupon.getShopId().equals(shopId)) {
            throw new ForbiddenException();
        }
        BeanUtils.copyProperties(param, smShopGroupon);
        updateById(smShopGroupon);
        return smShopGroupon;
    }

    @Override
    public void delete(Long id, Long shopId) {
        SmShopGroupon smShopGroupon = getById(id);
        if (smShopGroupon == null || !smShopGroupon.getShopId().equals(shopId)) {
            throw new ForbiddenException();
        }
        removeById(id);
    }

    @Override
    public String validOrder(OmOrderParam param, Long userId, Long orderId) throws JsonProcessingException {
        OmShopGrouponBodyParam body = param.getShopGrouponBody();
        if (body == null) {
            throw new ParameterException("shopGrouponBody 不能为空");
        }

        SmShopGroupon shopGroupon = getById(body.getGrouponId());
        if (shopGroupon == null) {
            throw new NotFoundException("团购信息不存在");
        }
//        BigDecimal onePrice = iHmStylistServiceRelationService.getPriceByServiceIdStylistIdAndSex(shopGroupon.getServiceId(), body.getStylistId(), body.getGenderType());
//        BigDecimal originPrice = DecimalUtils.multiply(onePrice, new BigDecimal(shopGroupon.getCount()));
//        BigDecimal realAmount = DecimalUtils.multiply(originPrice, shopGroupon.getDiscount());
        BigDecimal realAmount = shopGroupon.getAmount();

        if (body.getCouponId() != null) {
            //使用优惠券并验证优惠金额
            BigDecimal couponAmount = iSmUserCouponService.useCoupon(body.getCouponId(), userId, orderId, body.getShopId(), realAmount);
            realAmount = DecimalUtils.subtract(realAmount, couponAmount);
        }

        if (DecimalUtils.ne(realAmount,param.getAmount())){
            throw new ParameterException("实际金额不符");
        }

        OrderGrouponDTO dto = new OrderGrouponDTO();
        dto.setCount(shopGroupon.getCount());
        dto.setServiceId(shopGroupon.getServiceId());
        dto.setAmount(realAmount);
        dto.setStylistId(body.getStylistId());
        dto.setShopId(body.getShopId());
        dto.setGenderType(body.getGenderType());
        return objectMapper.writeValueAsString(dto);

//        List<OrderGrouponItemDTO> items = new ArrayList<>();
//        if (body == null){
//            throw new ParameterException("shopGrouponBody 不能为空");
//        }
//        List<SmShopGroupon> groupons = listByIds(body.getGrouponIds());
//        if (CollectionUtils.isEmpty(groupons) || groupons.size() != body.getGrouponIds().size()){
//            throw new ParameterException("团购信息不符");
//        }
//        BigDecimal totalOriginPrice = new BigDecimal("0");
//        BigDecimal totalRealPrice = new BigDecimal("0");
//
//        for (SmShopGroupon groupon : groupons) {
//            BigDecimal onePrice = iHmStylistServiceRelationService.getPriceByServiceIdStylistIdAndSex(groupon.getServiceId(), body.getStylistId(), body.getSexType());
//            BigDecimal originPrice = DecimalUtils.multiply(onePrice,new BigDecimal(groupon.getCount()));
//            totalOriginPrice = DecimalUtils.add(totalOriginPrice,originPrice);
//            BigDecimal  realAmount = DecimalUtils.multiply(originPrice,groupon.getDiscount());
//            totalRealPrice = DecimalUtils.add(totalRealPrice,realAmount);
//            OrderGrouponItemDTO dto = new OrderGrouponItemDTO();
//            dto.setAmount(realAmount);
//            dto.setCount(groupon.getCount());
//            dto.setServiceId(groupon.getServiceId());
//            items.add(dto);
//        }
//
//        if (body.getCouponId() != null) {
//            //使用优惠券并验证优惠金额
//            BigDecimal couponAmount = iSmUserCouponService.useCoupon(body.getCouponId(), userId, orderId, body.getShopId(), totalRealPrice);
//            totalRealPrice = DecimalUtils.subtract(totalRealPrice, couponAmount);
//        }
//
//        if (DecimalUtils.ne(totalRealPrice,param.getAmount())){
//            throw new ParameterException("实际金额不符");
//        }
//
//        OrderGrouponDTO dto = new OrderGrouponDTO();
//        dto.setItems(items);
//        dto.setShopId(body.getShopId());
//        dto.setStylistId(body.getStylistId());
//        return objectMapper.writeValueAsString(dto);
    }

    @Override
    public void updateGrouponsByStylist(Long stylistId, Long shopId, List<SmShopGrouponParam> groupons) {
        remove(new LambdaQueryWrapper<SmShopGroupon>()
                .eq(SmShopGroupon::getStylistId,stylistId)
                .eq(SmShopGroupon::getShopId,shopId));
        if (CollectionUtils.isNotEmpty(groupons)){
            List<SmShopGroupon> grouponList = groupons.stream().flatMap((Function<SmShopGrouponParam, Stream<SmShopGroupon>>) smShopGrouponParam -> {
                SmShopGroupon groupon = new SmShopGroupon();
                BeanUtils.copyProperties(smShopGrouponParam, groupon);
                groupon.setShopId(shopId);
                groupon.setStylistId(stylistId);
                return Stream.of(groupon);
            }).collect(Collectors.toList());
            saveBatch(grouponList);
        }
    }

    @Override
    public void removeByStylistId(Long stylistId) {
        remove(new LambdaQueryWrapper<SmShopGroupon>().eq(SmShopGroupon::getStylistId,stylistId));
    }

    @Override
    public List<SmShopGroupon> getByStylistId(Long stylistId) {
        return list(new LambdaQueryWrapper<SmShopGroupon>().eq(SmShopGroupon::getStylistId,stylistId));
    }
}
