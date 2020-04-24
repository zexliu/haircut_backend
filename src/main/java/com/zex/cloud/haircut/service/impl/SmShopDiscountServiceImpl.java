package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zex.cloud.haircut.entity.SmShopDiscount;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.mapper.SmShopDiscountMapper;
import com.zex.cloud.haircut.params.SmShopDiscountParam;
import com.zex.cloud.haircut.service.ISmShopDiscountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
@Service
public class SmShopDiscountServiceImpl extends ServiceImpl<SmShopDiscountMapper, SmShopDiscount> implements ISmShopDiscountService {

    @Override
    public SmShopDiscount create(SmShopDiscountParam param, Long shopId) {
        int count = count(new LambdaQueryWrapper<SmShopDiscount>().eq(SmShopDiscount::getShopId,shopId)
        .eq(SmShopDiscount::getServiceId,param.getServiceId()));
        if (count> 0 ){
            throw new ExistsException("该项服务已经有过折扣信息了");
        }
        SmShopDiscount discount = new SmShopDiscount();
        BeanUtils.copyProperties(param,discount);
        discount.setShopId(shopId);
        save(discount);
        return discount;
    }

    @Override
    public SmShopDiscount update(Long id, SmShopDiscountParam param, Long shopId) {
        SmShopDiscount discount = getById(id);
        if (!discount.getShopId().equals(shopId)){
            throw new ForbiddenException();
        }
        BeanUtils.copyProperties(param,discount);
        updateById(discount);
        return null;
    }

    @Override
    public void delete(Long id, Long shopId) {
        SmShopDiscount discount = getById(id);
        if (!discount.getShopId().equals(shopId)){
            throw new ForbiddenException();
        }
        removeById(id);
    }

    @Override
    public BigDecimal getDiscountByServiceIdAndShopId(Long serviceId, Long shopId) {
        SmShopDiscount smShopDiscount = getOne(new LambdaQueryWrapper<SmShopDiscount>().eq(SmShopDiscount::getShopId, shopId)
                .eq(SmShopDiscount::getServiceId, serviceId));
        if (smShopDiscount == null){
            throw new NotFoundException("折扣信息不存在 serviceId = "+ serviceId +"shopId = "+ shopId);
        }
        return smShopDiscount.getDiscount();
    }

    @Override
    public void batch(List<SmShopDiscountParam> params,Long shopId) {
        remove(new LambdaUpdateWrapper<SmShopDiscount>().eq(SmShopDiscount::getShopId,shopId));

        List<SmShopDiscount> collect = params.stream().flatMap(new Function<SmShopDiscountParam, Stream<SmShopDiscount>>() {
            @Override
            public Stream<SmShopDiscount> apply(SmShopDiscountParam param) {
                SmShopDiscount discount = new SmShopDiscount();
                BeanUtils.copyProperties(param, discount);
                discount.setShopId(shopId);

                return Stream.of(discount);
            }
        }).collect(Collectors.toList());
        saveBatch(collect);
    }

    @Override
    public List<SmShopDiscount> getByShopId(Long shopId) {
        return list(new LambdaQueryWrapper<SmShopDiscount>().eq(SmShopDiscount::getShopId,shopId));
    }
}
