package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.WeekDay;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class SmHalfTimeParam  {

    private WeekDay weekDay;

    private LocalTime startAt;

    private LocalTime endAt;

}
