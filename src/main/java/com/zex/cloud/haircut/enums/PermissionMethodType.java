package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import org.springframework.web.bind.annotation.RequestMethod;

public enum PermissionMethodType implements IEnum<Integer> {

    GET(1),
    POST(2),
    PUT(3),
    DELETE(4);
    PermissionMethodType(int code) {
        this.value = code;
    }
    private int value;

    public static PermissionMethodType adapt(RequestMethod requestMethod) {
        return valueOf(requestMethod.name());
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}