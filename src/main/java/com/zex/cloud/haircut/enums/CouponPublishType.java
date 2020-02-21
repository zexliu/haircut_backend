package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

//优惠券发放方式
public enum  CouponPublishType implements IEnum<Integer> {

    //用户领取  店铺发放
    USER_PULL(1),SHOP_PUSH(2);

    CouponPublishType(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}