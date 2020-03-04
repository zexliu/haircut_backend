package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.dto.BarPoint;
import com.zex.cloud.haircut.dto.PiePoint;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IStatisticsService {
    Map<String, Object> numbers(LocalDate startAt, LocalDate endAt);

    Map<String, Object> brokenLines(LocalDate startAt, LocalDate endAt, String type);

    List<PiePoint>  pie(LocalDate startAt, LocalDate endAt);

    List<BarPoint> bar(LocalDate startAt, LocalDate endAt);
}
