package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum  CommentTopicType  implements IEnum<Integer> {

    ORDER(1),NEWS(2);

    CommentTopicType(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}