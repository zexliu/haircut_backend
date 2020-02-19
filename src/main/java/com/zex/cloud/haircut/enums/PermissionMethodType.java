package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum PermissionMethodType implements IEnum<Integer> {

    GET(1),
    POST(2),
    PUT(3),
    DELETE(4);
    PermissionMethodType(int code) {
        this.value = code;
    }
    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}