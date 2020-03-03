package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum UserRewardStatus implements IEnum<Integer> {

    PENDING_REWARD(1),REWARDED(2);

    UserRewardStatus(int code) {
        this.value = code;
    }

    private int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}