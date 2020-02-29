package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum OrderType implements IEnum<Integer> {

    SHOP_SERVICE(1,"店铺服务"),
    SHOP_GROUPON(2,"团购服务"),
    USER_RECHARGE(3,"用户充值"),
    USER_FLOWER(4,"购买鲜花");

    OrderType(int value) {
        this.value = value;
    }

    OrderType(int code, String subject) {
        this.value = code;
    }
    private int value;

    private String subject;
    @Override
    public Integer getValue() {
        return this.value;
    }

}