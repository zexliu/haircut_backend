package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SyUserSocial;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-05
 */
public interface ISyUserSocialService extends IService<SyUserSocial> {

    SyUserSocial getByOpenId(String openId, String appId);

}
