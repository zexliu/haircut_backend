package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SyUserExtension;
import com.zex.cloud.haircut.mapper.SyUserExtensionMapper;
import com.zex.cloud.haircut.service.ISyUserExtensionService;
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
public class SyUserExtensionServiceImpl extends ServiceImpl<SyUserExtensionMapper, SyUserExtension> implements ISyUserExtensionService {

    @Override
    public SyUserExtension getByUnionId(String unionId) {
        return getOne(new LambdaQueryWrapper<SyUserExtension>().eq(SyUserExtension::getUnionId,unionId));
    }
}
