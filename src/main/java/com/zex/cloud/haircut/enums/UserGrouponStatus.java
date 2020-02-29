package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum UserGrouponStatus implements IEnum<Integer> {
    //购买  赠与
    PENDING_USE(1),USED(2),REFUND(3);

    UserGrouponStatus(int code) {
        this.value = code;
    }
    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
