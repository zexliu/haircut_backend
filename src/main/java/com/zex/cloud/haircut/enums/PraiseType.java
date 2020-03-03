package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum PraiseType implements IEnum<Integer> {

    COMMENT(1),REWARD(2);

    PraiseType(int code) {
        this.value = code;
    }

    private int value;
    @Override
    public Integer getValue() {
        return this.value;
    }

}