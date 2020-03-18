package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.UmVisitorsLog;
import com.zex.cloud.haircut.mapper.UmVisitorsLogMapper;
import com.zex.cloud.haircut.service.IUmVisitorsLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.vo.ShopVisitorsVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-11
 */
@Service
public class UmVisitorsLogServiceImpl extends ServiceImpl<UmVisitorsLogMapper, UmVisitorsLog> implements IUmVisitorsLogService {

    @Override
    public ShopVisitorsVO shopVisitors(Long shopId) {
        ShopVisitorsVO visitorsVO = new ShopVisitorsVO();
        int totalAmount = count(new LambdaQueryWrapper<>());
        int todayAmount = count(new LambdaQueryWrapper<UmVisitorsLog>()
        .ge(UmVisitorsLog::getCreateAt, LocalDate.now())
        .le(UmVisitorsLog::getCreateAt,LocalDate.now().plusDays(1)));
        visitorsVO.setTodayAmount(todayAmount);
        visitorsVO.setTotalAmount(totalAmount);
        return visitorsVO;
    }
}
