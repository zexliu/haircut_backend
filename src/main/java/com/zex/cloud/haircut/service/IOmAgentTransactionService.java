package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.OmAgentTransaction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.WithDrawParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
public interface IOmAgentTransactionService extends IService<OmAgentTransaction> {

    BigDecimal income(Long id, LocalDate startAt, LocalDate endAt);

    BigDecimal balance(Long id);

    List<BrokenLinePoint> brokenLines(LocalDate startAt, LocalDate endAt, Long id);

    void withdrawal(WithDrawParam param, Long id);

}
