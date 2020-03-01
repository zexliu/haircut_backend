package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.entity.SmCommissionDiscount;
import com.zex.cloud.haircut.mapper.SmCommissionDiscountMapper;
import com.zex.cloud.haircut.params.SmCommissionDiscountParam;
import com.zex.cloud.haircut.service.ISmCommissionDiscountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-01
 */
@Service
public class SmCommissionDiscountServiceImpl extends ServiceImpl<SmCommissionDiscountMapper, SmCommissionDiscount> implements ISmCommissionDiscountService {

    @Override
    public SmCommissionDiscount save(SmCommissionDiscountParam param, Long userId, String ip) {
        SmCommissionDiscount smCommissionDiscount = new SmCommissionDiscount();
        smCommissionDiscount.setCount(param.getCount());
        smCommissionDiscount.setDiscount(param.getDiscount());
        smCommissionDiscount.setOperatorIp(ip);
        smCommissionDiscount.setOperatorId(userId);
        smCommissionDiscount.setOperatorAt(LocalDateTime.now());
        save(smCommissionDiscount);
        return smCommissionDiscount;
    }

    @Override
    public SmCommissionDiscount update(Long id, SmCommissionDiscountParam param, Long userID, String ip) {
        SmCommissionDiscount smCommissionDiscount = new SmCommissionDiscount();
        smCommissionDiscount.setCount(param.getCount());
        smCommissionDiscount.setDiscount(param.getDiscount());
        smCommissionDiscount.setOperatorIp(ip);
        smCommissionDiscount.setOperatorId(userID);
        smCommissionDiscount.setId(id);
        smCommissionDiscount.setOperatorAt(LocalDateTime.now());
        updateById(smCommissionDiscount);
        return smCommissionDiscount;
    }
}
