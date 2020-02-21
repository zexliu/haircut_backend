package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

//优惠券领取限制
public enum  CouponPullLimitStatus implements IEnum<Integer> {

    //不限制  每个用户只能领取一次 , 每个用户每天可领取一次
    NONE(1),ONCE(2);

    CouponPullLimitStatus(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}