package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum OrderCodeType implements IEnum<Integer> {

    SERVICE(1), GROUPON(2);

    OrderCodeType(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}