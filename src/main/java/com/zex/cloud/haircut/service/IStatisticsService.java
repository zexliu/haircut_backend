package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.dto.BarPoint;
import com.zex.cloud.haircut.dto.PiePoint;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IStatisticsService {
    Map<String, Object> numbers(LocalDate startAt, LocalDate endAt);

    Map<String, Object> brokenLines(LocalDate startAt, LocalDate endAt, String type);

    List<PiePoint>  pie(LocalDate startAt, LocalDate endAt);

    List<BarPoint> bar(LocalDate startAt, LocalDate endAt);

    Map<String, Object> numbersAgent(LocalDate startAt, LocalDate endAt, Long id, Integer provinceCode, Integer code);

    Map<String, Object> brokenLinesAgent(LocalDate startAt, LocalDate endAt, String type, Long id, Integer provinceCode, Integer cityCode);

    List<PiePoint> pieAgent(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode);

    List<BarPoint> barAgent(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode);

}
