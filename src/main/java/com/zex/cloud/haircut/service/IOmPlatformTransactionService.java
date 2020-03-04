package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.OmPlatformTransaction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-03
 */
public interface IOmPlatformTransactionService extends IService<OmPlatformTransaction> {

    BigDecimal income(LocalDate startAt, LocalDate endAt);


    List<BrokenLinePoint> brokenLines(LocalDate startAt, LocalDate endAt);

}
