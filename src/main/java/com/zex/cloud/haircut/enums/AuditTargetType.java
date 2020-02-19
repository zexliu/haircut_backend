package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum  AuditTargetType implements IEnum<Integer> {

    SHOP(1);

    AuditTargetType(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}