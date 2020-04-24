package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum ClientTargetType implements IEnum<Integer> {

    SHOP(1),AGENT(2),USER(3);

    ClientTargetType(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}