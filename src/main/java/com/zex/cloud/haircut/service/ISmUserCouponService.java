package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmUserCoupon;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.response.SmUserCouponDetail;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-21
 */
public interface ISmUserCouponService extends IService<SmUserCoupon> {

    IPage<SmUserCouponDetail> list(Long userId, Long shopId, Boolean useStatus, Boolean dateStatus);

    SmUserCoupon userPull(Long couponId, Long userId);

    SmUserCoupon shopPush(Long couponId, Long shopId, Long userId);
}
