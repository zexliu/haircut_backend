package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum GenderType implements IEnum<Integer> {

    //现金券  满减卷
    UN_KNOW(0),MALE(1), FEMALE(2);

    GenderType(int code) {
        this.value = code;
    }
    private int value;

    public static GenderType getByValue(Integer gender) {
        for (GenderType value : values()) {
            if (value.getValue().equals(gender)){
                return value;
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}