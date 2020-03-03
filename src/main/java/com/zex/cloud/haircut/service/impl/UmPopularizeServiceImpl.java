package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.UmPopularize;
import com.zex.cloud.haircut.enums.PopularizeStatus;
import com.zex.cloud.haircut.enums.PopularizeType;
import com.zex.cloud.haircut.mapper.UmPopularizeMapper;
import com.zex.cloud.haircut.response.UmPopularizeUser;
import com.zex.cloud.haircut.service.IUmPopularizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class UmPopularizeServiceImpl extends ServiceImpl<UmPopularizeMapper, UmPopularize> implements IUmPopularizeService {

    @Override
    public IPage<UmPopularizeUser> page(Page<UmPopularizeUser> page, Long targetId, PopularizeType popularizeType, PopularizeStatus popularizeStatus, LocalDateTime startAt, LocalDateTime endAt) {
        return baseMapper.list(page,targetId,popularizeStatus,popularizeType,startAt,endAt);
    }
}
