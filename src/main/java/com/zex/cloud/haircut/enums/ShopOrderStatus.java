package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum ShopOrderStatus implements IEnum<Integer> {
    CANCEL(0),//已取消
    PENDING_PAY(1), //待支付
    PENDING_USE(2), //待使用
    PENDING_EVALUATION(3),//待评价
    FINISH(4),;//已完成
    ShopOrderStatus(int code) {
        this.value = code;
    }
    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}