package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmBasicSetting;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.SmBasicSettingParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-01
 */
public interface ISmBasicSettingService extends IService<SmBasicSetting> {

    SmBasicSetting current();

    SmBasicSetting save(SmBasicSettingParam param, Long userId, String ip);

//    SmBasicSetting update(Long id, SmBasicSettingParam param, Long userId, String ip);
}
