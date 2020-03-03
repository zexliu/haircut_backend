package com.zex.cloud.haircut.controller;

import com.zex.cloud.haircut.service.IStatisticsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@Api(tags = "统计相关")
@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {
    @Autowired
    private IStatisticsService iStatisticsService;

    @GetMapping("/numbers")
    public Map<String, Object> numbers(LocalDateTime startAt, LocalDateTime endAt) {
        return iStatisticsService.numbers(startAt,endAt);
    }


}
