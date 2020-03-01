package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmCommissionDiscount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.SmCommissionDiscountParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-01
 */
public interface ISmCommissionDiscountService extends IService<SmCommissionDiscount> {

    SmCommissionDiscount save(SmCommissionDiscountParam param, Long userId, String ip);

    SmCommissionDiscount update(Long id, SmCommissionDiscountParam param,Long userID, String ip);
}
