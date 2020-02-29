package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum SexType implements IEnum<Integer> {

    //现金券  满减卷
    MALE(1), FEMALE(0);

    SexType(int code) {
        this.value = code;
    }
    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}