package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.entity.SmShopGroup;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.mapper.SmShopGroupMapper;
import com.zex.cloud.haircut.params.SmShopGroupParam;
import com.zex.cloud.haircut.service.ISmShopGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
@Service
public class SmShopGroupServiceImpl extends ServiceImpl<SmShopGroupMapper, SmShopGroup> implements ISmShopGroupService {


    @Override
    public SmShopGroup create(SmShopGroupParam param, Long shopId) {
        SmShopGroup smShopGroup = new SmShopGroup();
        BeanUtils.copyProperties(param,smShopGroup);
        smShopGroup.setShopId(shopId);
        save(smShopGroup);
        return smShopGroup;
    }

    @Override
    public SmShopGroup update(Long id, SmShopGroupParam param, Long shopId) {
        SmShopGroup smShopGroup = getById(id);
        if (smShopGroup == null || !smShopGroup.getShopId().equals(shopId)){
            throw new ForbiddenException();
        }
        BeanUtils.copyProperties(param,smShopGroup);
        updateById(smShopGroup);
        return smShopGroup;
    }

    @Override
    public void delete(Long id, Long shopId) {
        SmShopGroup smShopGroup = getById(id);
        if (smShopGroup == null || !smShopGroup.getShopId().equals(shopId)){
            throw new ForbiddenException();
        }
        removeById(id);
    }
}
