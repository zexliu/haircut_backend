package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SmHalfTime;
import com.zex.cloud.haircut.mapper.SmHalfTimeMapper;
import com.zex.cloud.haircut.params.SmHalfTimeParam;
import com.zex.cloud.haircut.service.ISmHalfTimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-21
 */
@Service
public class SmHalfTimeServiceImpl extends ServiceImpl<SmHalfTimeMapper, SmHalfTime> implements ISmHalfTimeService {

    @Override
    public void updateRelations(Long shopId, List<SmHalfTimeParam> params) {
        remove(new LambdaQueryWrapper<SmHalfTime>().eq(SmHalfTime::getShopId,shopId));
        if (CollectionUtils.isNotEmpty(params)){
            List<SmHalfTime> list = params.stream()
                    .flatMap((Function<SmHalfTimeParam, Stream<SmHalfTime>>) smHalfTimeParam -> {
                SmHalfTime time = new SmHalfTime();
                time.setShopId(shopId);
                time.setStartAt(smHalfTimeParam.getStartAt());
                time.setEndAt(smHalfTimeParam.getEndAt());
                time.setWeekDay(smHalfTimeParam.getWeekDay());
                return Stream.of(time);
            }).collect(Collectors.toList());
            saveBatch(list);
        }
    }
}
