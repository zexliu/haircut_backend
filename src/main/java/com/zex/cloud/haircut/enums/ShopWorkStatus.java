package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum ShopWorkStatus implements IEnum<Integer> {

    WORK(1),
    REST(0)
    ;
    ShopWorkStatus(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}