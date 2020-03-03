package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.OmPlatformTransaction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-03
 */
public interface IOmPlatformTransactionService extends IService<OmPlatformTransaction> {

    BigDecimal income(LocalDateTime startAt, LocalDateTime endAt);


}
