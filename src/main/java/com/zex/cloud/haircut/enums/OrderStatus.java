package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum OrderStatus implements IEnum<Integer> {

    PENDING(1), //等待支付
    PAID(2), //已支付
    REFUND(3),//已退款
    EXPIRE(4),//已超时
    CANCEL(5),;//已取消
    OrderStatus(int code) {
        this.value = code;
    }
    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}