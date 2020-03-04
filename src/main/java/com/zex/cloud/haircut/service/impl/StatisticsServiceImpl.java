package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.dto.BarPoint;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.dto.PiePoint;
import com.zex.cloud.haircut.enums.OrderType;
import com.zex.cloud.haircut.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public Map<String, Object> numbers(LocalDate startAt, LocalDate endAt) {
        int users = iSyUserService.count(startAt,endAt);

        int shops = iSmShopService.count(startAt,endAt);

        BigDecimal transactions = iOmPlatformTransactionService.income(startAt,endAt);

        BigDecimal orders = iOmOrderService.income(startAt,endAt, null);
        Map<String,Object> map= new HashMap<>();
        map.put("users",users);
        map.put("shops",shops);
        map.put("transactions",transactions);
        map.put("orders",orders);
        return map;
    }

    @Override
    public Map<String, Object> brokenLines(LocalDate startAt, LocalDate endAt, String type) {
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.equals("users",type)){
            List<BrokenLinePoint> users = iSyUserService.brokenLines(startAt,endAt);
            map.put("users",users);
        }else if (StringUtils.equals("shops",type)){
            List<BrokenLinePoint> shops = iSmShopService.brokenLines(startAt,endAt);
            map.put("shops",shops);
        }else if (StringUtils.equals("transactions",type)){
            List<BrokenLinePoint> transactions = iOmPlatformTransactionService.brokenLines(startAt,endAt);
            map.put("transactions",transactions);
        }else if (StringUtils.equals("orders",type)){
            List<BrokenLinePoint> orders = iOmOrderService.brokenLines(startAt,endAt, null);
            map.put("orders",orders);

        }
        return map;
    }

    @Override
    public List<PiePoint> pie(LocalDate startAt, LocalDate endAt) {
        List<PiePoint>piePoints = new ArrayList<>();
        for (OrderType type : OrderType.values()) {
            PiePoint piePoint = new PiePoint();
            piePoint.setName(type.getSubject());
            BigDecimal value = iOmOrderService.income(startAt,endAt,type);
            piePoint.setValue(value);
            piePoints.add(piePoint);
        }
        return piePoints;
    }

    @Override
    public List<BarPoint> bar(LocalDate startAt, LocalDate endAt) {

        List<BarPoint> barPoints = new ArrayList<>();
        for (OrderType type : OrderType.values()) {
            BarPoint barPoint = new BarPoint();
            barPoint.setName(type.getSubject());
            List<BrokenLinePoint> brokenLinePoints = iOmOrderService.brokenLines(startAt, endAt,type);
            barPoint.setValues(brokenLinePoints);
            barPoints.add(barPoint);
        }
        return barPoints;
    }
}
