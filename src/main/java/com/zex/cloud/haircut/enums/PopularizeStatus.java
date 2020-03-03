package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum PopularizeStatus implements IEnum<Integer> {

    //成功, 待首次支付
    OK(1),PENDING_FIRST_PAY(2);

    PopularizeStatus(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}