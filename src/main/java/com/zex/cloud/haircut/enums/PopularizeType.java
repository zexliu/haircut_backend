package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum PopularizeType implements IEnum<Integer> {

    //成功, 待首次支付
    SHOP(1), USER(2);

    PopularizeType(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}