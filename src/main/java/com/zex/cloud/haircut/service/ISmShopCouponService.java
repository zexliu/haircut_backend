package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmShopCoupon;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.SmShopCouponParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-21
 */
public interface ISmShopCouponService extends IService<SmShopCoupon> {

    SmShopCoupon save(Long id, SmShopCouponParam param);

    void updateStatus(Long id, Long shopId, Boolean publishStatus);

}
