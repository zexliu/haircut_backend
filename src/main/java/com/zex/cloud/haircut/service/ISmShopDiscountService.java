package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmShopDiscount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.SmShopDiscountParam;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface ISmShopDiscountService extends IService<SmShopDiscount> {

    SmShopDiscount create(SmShopDiscountParam param, Long shopId);

    SmShopDiscount update(Long id, SmShopDiscountParam param, Long shopId);

    void delete(Long id, Long shopId);

    BigDecimal getDiscountByServiceIdAndShopId(Long serviceId, Long shopId);

}
