package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.entity.SmBasicSetting;
import com.zex.cloud.haircut.mapper.SmBasicSettingMapper;
import com.zex.cloud.haircut.params.SmBasicSettingParam;
import com.zex.cloud.haircut.service.ISmBasicSettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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
public class SmBasicSettingServiceImpl extends ServiceImpl<SmBasicSettingMapper, SmBasicSetting> implements ISmBasicSettingService {

    @Override
    public SmBasicSetting current() {
        return baseMapper.current();
    }

    @Override
    public SmBasicSetting save(SmBasicSettingParam param, Long userId, String ip) {
        SmBasicSetting setting = new SmBasicSetting();
        BeanUtils.copyProperties(param,setting);
        setting.setOperatorAt(LocalDateTime.now());
        setting.setOperatorId(userId);
        setting.setOperatorIp(ip);
        save(setting);
        return setting;
    }

//    @Override
//    public SmBasicSetting update(Long id, SmBasicSettingParam param, Long userId, String ip) {
//        SmBasicSetting setting = new SmBasicSetting();
//        BeanUtils.copyProperties(param,setting);
//        setting.setId(id);
//        setting.setOperatorAt(LocalDateTime.now());
//        setting.setOperatorId(userId);
//        setting.setOperatorIp(ip);
//        updateById(setting);
//        return setting;
//    }
}
