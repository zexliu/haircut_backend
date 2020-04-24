package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmAgent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.entity.SmAgentApply;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
public interface ISmAgentService extends IService<SmAgent> {

    SmAgent customSave(SmAgent smAgent);

    SmAgent getByUserId(Long id);

    SmAgent getByLocation(Integer provinceCode, Integer cityCode);

    void updateEnableStatus(Boolean enableStatus, Long id);
}
