package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmShopApply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.SmShopApplyParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
public interface ISmShopApplyService extends IService<SmShopApply> {

    SmShopApply create(SmShopApplyParam param, Long userId);

    SmShopApply getApplyByUserId(Long userId);

    SmShopApply update(SmShopApplyParam param, Long userId);
}
