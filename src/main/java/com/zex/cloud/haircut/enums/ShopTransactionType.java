package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum ShopTransactionType implements IEnum<Integer> {
    //充值  购物  退款
    SERVICE_INCOME(1), GROUP_INCOME(2),WITHDRAW(3),REWARD(4);

    ShopTransactionType(int code) {
        this.value = code;
    }
    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
