package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.dto.BarPoint;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.dto.PiePoint;
import com.zex.cloud.haircut.enums.OrderType;
import com.zex.cloud.haircut.service.*;
import com.zex.cloud.haircut.util.DecimalUtils;
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
    private IOmAgentTransactionService iOmAgentTransactionService;
    @Autowired
    private IOmOrderService iOmOrderService;

    @Autowired
    private IOmShopOrderService iOmShopOrderService;

    @Autowired
    private IOmUserGrouponService iOmUserGrouponService;
    @Override
    public Map<String, Object> numbers(LocalDate startAt, LocalDate endAt) {
        int users = iSyUserService.count(startAt,endAt);
        int shops = iSmShopService.count(startAt,endAt, null, null);
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
            List<BrokenLinePoint> shops = iSmShopService.brokenLines(startAt,endAt, null, null);
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

    @Override
    public Map<String, Object> numbersAgent(LocalDate startAt, LocalDate endAt, Long id, Integer provinceCode, Integer cityCode) {
        int shops = iSmShopService.count(startAt,endAt,provinceCode,cityCode);
        BigDecimal transactions = iOmAgentTransactionService.income(id,startAt,endAt);
        BigDecimal service = iOmShopOrderService.income(startAt,endAt,provinceCode,cityCode);
        BigDecimal group = iOmUserGrouponService.income(startAt,endAt,provinceCode,cityCode);
        BigDecimal balance = iOmAgentTransactionService.balance(id);
        Map<String,Object> map= new HashMap<>();
        map.put("shops",shops);
        map.put("transactions",transactions);
        map.put("balance",balance);
        map.put("orders", DecimalUtils.add(service,group));
        return map;
    }

    @Override
    public Map<String, Object> brokenLinesAgent(LocalDate startAt, LocalDate endAt, String type, Long id, Integer provinceCode, Integer cityCode) {
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.equals("shops",type)){
            List<BrokenLinePoint> shops = iSmShopService.brokenLines(startAt,endAt,provinceCode,cityCode);
            map.put("shops",shops);
        }else if (StringUtils.equals("transactions",type)){
            List<BrokenLinePoint> transactions = iOmAgentTransactionService.brokenLines(startAt,endAt,id);
            map.put("transactions",transactions);
        }else if (StringUtils.equals("orders",type)){
            List<BrokenLinePoint> service = iOmShopOrderService.brokenLinesAgent(startAt,endAt, provinceCode,cityCode);
            map.put("services",service);
            List<BrokenLinePoint> groupon = iOmUserGrouponService.brokenLinesAgent(startAt,endAt, provinceCode,cityCode);
            map.put("groupons",groupon);

        }
        return map;
    }

    @Override
    public List<PiePoint> pieAgent(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode) {
        List<PiePoint>piePoints = new ArrayList<>();
        PiePoint piePoint = new PiePoint();
        piePoint.setName("服务订单");
        BigDecimal value = iOmShopOrderService.income(startAt,endAt,provinceCode,cityCode);
        piePoint.setValue(value);
        piePoints.add(piePoint);
        PiePoint piePoint1 = new PiePoint();
        piePoint1.setName("团购订单");
        BigDecimal value1 = iOmUserGrouponService.income(null,null,provinceCode,cityCode);
        piePoint1.setValue(value1);
        piePoints.add(piePoint1);

//        for (OrderType type : OrderType.values()) {
//            PiePoint piePoint = new PiePoint();
//            piePoint.setName(type.getSubject());
//            BigDecimal value = iOmOrderService.income(startAt,endAt,type);
//            piePoint.setValue(value);
//            piePoints.add(piePoint);
//        }
        return piePoints;
    }

    @Override
    public List<BarPoint> barAgent(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode) {
        List<BarPoint> barPoints = new ArrayList<>();
        BarPoint barPoint = new BarPoint();
        barPoint.setName("服务订单");
        List<BrokenLinePoint> value = iOmShopOrderService.brokenLinesAgent(startAt, endAt,provinceCode,cityCode);
        barPoint.setValues(value);
        barPoints.add(barPoint);
        BarPoint barPoint1 = new BarPoint();
        barPoint1.setName("服务订单");
        List<BrokenLinePoint> value1 = iOmShopOrderService.brokenLinesAgent(startAt, endAt,provinceCode,cityCode);
        barPoint1.setValues(value1);
        barPoints.add(barPoint1);
        return barPoints;
    }
}
