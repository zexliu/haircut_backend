package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.entity.SmShop;
import com.zex.cloud.haircut.entity.SyUser;
import com.zex.cloud.haircut.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements IStatisticsService {
    @Autowired
    private ISyUserService iSyUserService;

    @Autowired
    private ISmShopService iSmShopService;

    @Autowired
    private IOmPlatformTransactionService iOmPlatformTransactionService;

    @Autowired
    private IOmOrderService iOmOrderService;
    @Override
    public Map<String, Object> numbers(LocalDateTime startAt, LocalDateTime endAt) {
        int users = iSyUserService.count(new LambdaQueryWrapper<SyUser>()
                .ge(startAt != null, SyUser::getCreateAt, startAt)
                .le(endAt != null, SyUser::getCreateAt, endAt));

        int shops = iSmShopService.count(new LambdaQueryWrapper<SmShop>()
                .ge(startAt != null, SmShop::getCreateAt, startAt)
                .le(endAt != null, SmShop::getCreateAt, endAt));

        BigDecimal transactions = iOmPlatformTransactionService.income(startAt,endAt);

        if (transactions == null){
            transactions = new BigDecimal("0");
        }
        int orders = iOmOrderService.count(new LambdaQueryWrapper<OmOrder>()
                .ge(startAt != null, OmOrder::getCreateAt, startAt)
                .le(endAt != null, OmOrder::getCreateAt, endAt));
        Map<String,Object> map= new HashMap<>();
        map.put("users",users);
        map.put("shops",shops);
        map.put("transactions",transactions);
        map.put("orders",orders);
        return map;
    }
}
