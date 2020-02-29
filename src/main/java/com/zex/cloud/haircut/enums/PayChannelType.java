package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum  PayChannelType implements IEnum<Integer> {

    WEI_XIN(1),
    WALLET(2),;

    PayChannelType(int value) {
        this.value = value;
    }
    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}