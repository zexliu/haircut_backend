package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SmHalfTime;
import com.zex.cloud.haircut.entity.SmShop;
import com.zex.cloud.haircut.enums.WeekDay;
import com.zex.cloud.haircut.mapper.SmHalfTimeMapper;
import com.zex.cloud.haircut.params.SmHalfTimeParam;
import com.zex.cloud.haircut.service.ISmHalfTimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.service.ISmShopService;
import com.zex.cloud.haircut.util.DateTimeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-21
 */
@Service
public class SmHalfTimeServiceImpl extends ServiceImpl<SmHalfTimeMapper, SmHalfTime> implements ISmHalfTimeService {

    @Autowired
    private ISmShopService iSmShopService;
//    @Override
//    public void updateRelations(Long shopId, List<SmHalfTimeParam> params) {
//        remove(new LambdaQueryWrapper<SmHalfTime>().eq(SmHalfTime::getShopId, shopId));
//        if (CollectionUtils.isNotEmpty(params)) {
//            List<SmHalfTime> list = params.stream()
//                    .flatMap((Function<SmHalfTimeParam, Stream<SmHalfTime>>) smHalfTimeParam -> {
//                        SmHalfTime time = new SmHalfTime();
//                        time.setShopId(shopId);
//                        time.setStartAt(smHalfTimeParam.getStartAt());
//                        time.setEndAt(smHalfTimeParam.getEndAt());
//                        time.setWeekDay(smHalfTimeParam.getWeekDay());
//                        return Stream.of(time);
//                    }).collect(Collectors.toList());
//            saveBatch(list);
//        }
//    }

    @Override
    public boolean valid(Long shopId, LocalDateTime appointmentAt) {
        SmShop smShop  = iSmShopService.getById(shopId);
        if (smShop == null){
            return false;
        }
        if (smShop.getHalfStatus() == null || !smShop.getHalfStatus()){
            return false;
        }
        LocalTime localTime = DateTimeUtils.localDateTimeToLocalTime(appointmentAt);
       int count =  count(new LambdaQueryWrapper<SmHalfTime>()
                .eq(SmHalfTime::getWeekDay, WeekDay.adapt(appointmentAt.getDayOfWeek()))
                .le(SmHalfTime::getStartAt, localTime)
                .ge(SmHalfTime::getEndAt, localTime));
       return count > 0;

    }

    @Override
    public SmHalfTime create(SmHalfTimeParam param) {
        SmHalfTime halfTime = new SmHalfTime();
        BeanUtils.copyProperties(param,halfTime);
        save(halfTime);
        return halfTime;
    }

    @Override
    public SmHalfTime update(Long id, SmHalfTimeParam param) {
        SmHalfTime halfTime = new SmHalfTime();
        BeanUtils.copyProperties(param,halfTime);
        halfTime.setId(id);
        updateById(halfTime);
        return halfTime;
    }

    @Override
    public Boolean isHalf(LocalDateTime dateTime) {
        LocalTime localTime = DateTimeUtils.localDateTimeToLocalTime(dateTime);
        int count =  count(new LambdaQueryWrapper<SmHalfTime>()
                .eq(SmHalfTime::getWeekDay, WeekDay.adapt(dateTime.getDayOfWeek()))
                .le(SmHalfTime::getStartAt, localTime)
                .ge(SmHalfTime::getEndAt, localTime));
        return count > 0;

    }


}
