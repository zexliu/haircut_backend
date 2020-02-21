package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

//优惠券类型
public enum CouponType implements IEnum<Integer> {

    //现金券  满减卷
    CASH(1), FULL_REDUCTION(2);

    CouponType(int code) {
        this.value = code;
    }
    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}