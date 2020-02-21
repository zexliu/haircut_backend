package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.entity.SmShopCoupon;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.mapper.SmShopCouponMapper;
import com.zex.cloud.haircut.params.SmShopCouponParam;
import com.zex.cloud.haircut.service.ISmShopCouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-21
 */
@Service
public class SmShopCouponServiceImpl extends ServiceImpl<SmShopCouponMapper, SmShopCoupon> implements ISmShopCouponService {

    @Override
    public SmShopCoupon save(Long shopId,SmShopCouponParam param) {
        SmShopCoupon smShopCoupon = new SmShopCoupon();
        BeanUtils.copyProperties(param,smShopCoupon);
        smShopCoupon.setShopId(shopId);
        smShopCoupon.setSurplusCount(param.getPublishCount());
        save(smShopCoupon);
        return smShopCoupon;
    }

    @Override
    public void updateStatus(Long id, Long shopId, Boolean publishStatus) {
        SmShopCoupon coupon = getById(id);
        if (!coupon.getShopId().equals(shopId)){
            throw new ForbiddenException();
        }
        coupon.setPublishStatus(publishStatus);
        updateById(coupon);
    }
}
