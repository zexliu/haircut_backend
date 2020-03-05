package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SyUserExtension;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-05
 */
public interface ISyUserExtensionService extends IService<SyUserExtension> {

    SyUserExtension getByUnionId(String unionId);
}
