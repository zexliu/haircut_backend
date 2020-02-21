package com.zex.cloud.haircut.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.time.DayOfWeek;

public enum WeekDay implements IEnum<Integer> {

    MONDAY(1),

    TUESDAY(2),

    WEDNESDAY(3),

    THURSDAY(4),

    FRIDAY(5),

    SATURDAY(6),

    SUNDAY(7);

    public static WeekDay adapt(DayOfWeek dayOfWeek){
        for (WeekDay day : WeekDay.values()) {
            if (day.getValue() == dayOfWeek.getValue()){
                return day;
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    WeekDay(int value) {
        this.value = value;
    }

    private int value;

}
