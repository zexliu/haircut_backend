package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum CommentFromType implements IEnum<Integer> {

    USER(1),SHOP(2);

    CommentFromType(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}