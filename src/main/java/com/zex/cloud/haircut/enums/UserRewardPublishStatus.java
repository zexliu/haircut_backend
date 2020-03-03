package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum UserRewardPublishStatus implements IEnum<Integer> {

    PENDING_PAY(1),PUBLISHED(2),CANCEL(3);

    UserRewardPublishStatus(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}