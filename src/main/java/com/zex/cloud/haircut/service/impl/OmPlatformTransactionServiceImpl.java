package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.entity.OmPlatformTransaction;
import com.zex.cloud.haircut.mapper.OmPlatformTransactionMapper;
import com.zex.cloud.haircut.service.IOmPlatformTransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-03
 */
@Service
public class OmPlatformTransactionServiceImpl extends ServiceImpl<OmPlatformTransactionMapper, OmPlatformTransaction> implements IOmPlatformTransactionService {

    @Override
    public BigDecimal income(LocalDateTime startAt, LocalDateTime endAt) {
        return baseMapper.income(startAt,endAt);
    }
}
