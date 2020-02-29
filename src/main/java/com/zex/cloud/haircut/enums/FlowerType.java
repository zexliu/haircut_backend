package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum FlowerType implements IEnum<Integer> {
    //购买  赠与
    BUY(1), GIFT(2);

    FlowerType(int code) {
        this.value = code;
    }
    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
