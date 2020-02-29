package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

public enum  CommentStarLevel implements IEnum {

    GOOD(1),MIDDLE(2),BAD(3),
    ;

    CommentStarLevel(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
