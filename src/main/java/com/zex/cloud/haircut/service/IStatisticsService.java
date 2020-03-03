package com.zex.cloud.haircut.service;

import java.time.LocalDateTime;
import java.util.Map;

public interface IStatisticsService {
    Map<String, Object> numbers(LocalDateTime startAt, LocalDateTime endAt);

}
