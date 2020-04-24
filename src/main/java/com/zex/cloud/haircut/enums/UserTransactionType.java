package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum UserTransactionType implements IEnum<Integer> {
    //充值  购物  退款
    CHARGE(1), SHOPPING(2),REFUND(3),REWARD(4),POPULARIZE(5),POPULARIZE_FIRST(6),WITHDRAW(7);


    UserTransactionType(int code) {
        this.value = code;
    }
    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
