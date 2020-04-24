package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum ShopTitleType implements IEnum<Integer> {
    //充值  购物  退款
    DEFAULT(1), SAFETY(2);

    ShopTitleType(int code) {
        this.value = code;
    }
    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
