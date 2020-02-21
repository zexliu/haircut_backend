package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

//优惠券领取用户限制
public enum  CouponMemberStatus implements IEnum<Integer> {

    //不限制   //新用户
    NONE(1),NEW(2);

    CouponMemberStatus(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}