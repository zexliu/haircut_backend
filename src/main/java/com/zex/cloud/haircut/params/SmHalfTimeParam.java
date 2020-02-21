package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.WeekDay;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmHalfTimeParam  {

    private WeekDay weekDay;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

}
