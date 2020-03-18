package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.WeekDay;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class SmHalfTimeParam  {

    @NotNull
    private WeekDay weekDay;
    @NotNull
    private LocalTime startAt;
    @NotNull
    private LocalTime endAt;

}
