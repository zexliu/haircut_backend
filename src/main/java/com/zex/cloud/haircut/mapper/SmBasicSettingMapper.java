package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.entity.SmBasicSetting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-03-01
 */
public interface SmBasicSettingMapper extends BaseMapper<SmBasicSetting> {

    SmBasicSetting current();

}
