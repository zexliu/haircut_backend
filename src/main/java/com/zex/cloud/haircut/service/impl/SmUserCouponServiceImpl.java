package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmShopCoupon;
import com.zex.cloud.haircut.entity.SmUserCoupon;
import com.zex.cloud.haircut.enums.CouponPublishType;
import com.zex.cloud.haircut.enums.CouponPullLimitStatus;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.mapper.SmUserCouponMapper;
import com.zex.cloud.haircut.response.SmUserCouponDetail;
import com.zex.cloud.haircut.service.ISmShopCouponService;
import com.zex.cloud.haircut.service.ISmUserCouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-21
 */
@Service
public class SmUserCouponServiceImpl extends ServiceImpl<SmUserCouponMapper, SmUserCoupon> implements ISmUserCouponService {

    @Autowired
    private ISmShopCouponService iSmShopCouponService;

    @Override
    public IPage<SmUserCouponDetail> list(Long userId, Long shopId, Boolean useStatus, Boolean dateStatus) {

        return baseMapper.list(userId, shopId, useStatus, dateStatus);
    }

    @Override
    @Transactional
    public SmUserCoupon userPull(Long couponId, Long userId) {
        SmShopCoupon coupon = iSmShopCouponService.getById(couponId);

        if (coupon == null){
            throw new NotFoundException();
        }
        if (coupon.getPullLimitStatus() == CouponPullLimitStatus.ONCE) {
           int count = count(new LambdaQueryWrapper<SmUserCoupon>()
           .eq(SmUserCoupon::getUserId,userId).eq(SmUserCoupon::getCouponId,couponId));
           if (count>0){
               throw new ExistsException("已领取该优惠券了");
           }
        }else {
            int count = count(new LambdaQueryWrapper<SmUserCoupon>()
                    .eq(SmUserCoupon::getUserId,userId).eq(SmUserCoupon::getCouponId,couponId).eq(SmUserCoupon::getUseStatus,false));
            if (count>0){
                throw new ExistsException("已有没使用的优惠券,不能重复领取");
            }
        }
        if (coupon.getPublishType()  != CouponPublishType.USER_PULL){
            throw new NotFoundException("该优惠券不可领取");
        }
        if (coupon.getSurplusCount() <= 0){
            throw new NotFoundException("该优惠券已经被领光了");
        }
        if (coupon.getPullStartAt().isAfter(LocalDateTime.now())){
            throw new ForbiddenException("还没到领取时间呢");
        }
        if (coupon.getPullEndAt().isBefore(LocalDateTime.now())){
            throw new ForbiddenException("该优惠券发放已经结束了");
        }

        return saveCoupon(couponId, userId, coupon);
    }

    private SmUserCoupon saveCoupon(Long couponId, Long userId, SmShopCoupon coupon) {
        SmUserCoupon smUserCoupon = new SmUserCoupon();
        smUserCoupon.setCouponId(couponId);
        smUserCoupon.setShopId(coupon.getShopId());
        smUserCoupon.setUserId(userId);
        smUserCoupon.setUseStatus(false);
        if (coupon.getAbsoluteStatus()) {
            smUserCoupon.setUseStartAt(coupon.getAbsoluteStartAt());
            smUserCoupon.setUseEndAt(coupon.getAbsoluteEndAt());
        } else {
            smUserCoupon.setUseStartAt(LocalDateTime.now());
            smUserCoupon.setUseEndAt(LocalDateTime.now().plusDays(coupon.getRelativeDay()));
        }
        coupon.setSurplusCount(coupon.getSurplusCount() - 1);
        iSmShopCouponService.updateById(coupon);
        save(smUserCoupon);
        return smUserCoupon;
    }

    @Override
    public SmUserCoupon shopPush(Long couponId, Long shopId, Long userId) {
        SmShopCoupon coupon = iSmShopCouponService.getById(couponId);

        if (coupon == null){
            throw new NotFoundException();
        }
        if (!coupon.getShopId().equals(shopId)){
            throw new ForbiddenException();
        }
        int count = count(new LambdaQueryWrapper<SmUserCoupon>()
                .eq(SmUserCoupon::getUserId,userId).eq(SmUserCoupon::getCouponId,couponId).eq(SmUserCoupon::getUseStatus,false));
        if (count>0){
            throw new ExistsException("用户已有没使用的优惠券,不能重复发放");
        }
        if (coupon.getPublishType()  != CouponPublishType.SHOP_PUSH){
            throw new NotFoundException("该优惠券不能主动发放");
        }
        if (coupon.getSurplusCount() <= 0){
            throw new NotFoundException("该优惠剩余数量不足");
        }

        return saveCoupon(couponId, userId, coupon);
    }
}
