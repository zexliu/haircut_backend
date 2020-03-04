package com.zex.cloud.haircut.controller;

import com.zex.cloud.haircut.dto.BarPoint;
import com.zex.cloud.haircut.dto.PiePoint;
import com.zex.cloud.haircut.service.IStatisticsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Api(tags = "统计相关")
@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {
    @Autowired
    private IStatisticsService iStatisticsService;

    @GetMapping("/numbers")
    public Map<String, Object> numbers(LocalDate startAt, LocalDate endAt) {
        return iStatisticsService.numbers(startAt,endAt);
    }
    @GetMapping("/brokenLines")
    public Map<String, Object> brokenLines(LocalDate startAt, LocalDate endAt,String type) {
        return iStatisticsService.brokenLines(startAt,endAt,type);
    }

    @GetMapping("/pie")
    public List<PiePoint> pie(LocalDate startAt, LocalDate endAt) {
        return iStatisticsService.pie(startAt,endAt);
    }

    @GetMapping("/bar")
    public List<BarPoint> bar(LocalDate startAt, LocalDate endAt) {
        return iStatisticsService.bar(startAt,endAt);
    }

}
