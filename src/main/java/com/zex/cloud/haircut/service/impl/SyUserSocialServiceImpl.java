package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SyUserSocial;
import com.zex.cloud.haircut.enums.SocialType;
import com.zex.cloud.haircut.mapper.SyUserSocialMapper;
import com.zex.cloud.haircut.service.ISyUserSocialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-05
 */
@Service
public class SyUserSocialServiceImpl extends ServiceImpl<SyUserSocialMapper, SyUserSocial> implements ISyUserSocialService {

    @Override
    public SyUserSocial getByOpenId(String openId, String appId) {
        return  getOne(new LambdaQueryWrapper<SyUserSocial>().eq(SyUserSocial::getAppId,appId)
        .eq(SyUserSocial::getOpenId,openId).eq(SyUserSocial::getSocialType, SocialType.WX));
    }
}
