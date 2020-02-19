package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.SmShop;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.mapper.SmShopMapper;
import com.zex.cloud.haircut.params.SmShopParam;
import com.zex.cloud.haircut.service.ISmShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
@Service
public class SmShopServiceImpl extends ServiceImpl<SmShopMapper, SmShop> implements ISmShopService {

    @Override
    public IPage<SmShop> list(Page<SmShop> convert, String keywords, ShopWorkStatus workStatus, String provinceCode, String cityCode, String districtCode, Double longitude, Double latitude) {
        return baseMapper.list(convert, keywords, workStatus, provinceCode, cityCode, districtCode, longitude, latitude);
    }

    @Override
    public SmShop customSave(SmShop smShop) {
        valid(null, smShop.getUserId());
        // TODO: 2020/2/19 保存角色信息
        save(smShop);
        return smShop;
    }

    @Override
    public SmShop update(Long id, SmShopParam param, Long userId) {
        SmShop smShop = getById(id);
        if (!smShop.getUserId().equals(userId)){
            throw new ForbiddenException();
        }
        smShop.setId(id);
        BeanUtils.copyProperties(param, smShop);
        updateById(smShop);
        return smShop;
    }



    @Override
    public SmShop adminUpdate(Long id, SmShopParam param) {
        SmShop smShop = getById(id);
        if (!param.getUserId().equals(smShop.getUserId())){
            valid(id,param.getUserId());
            // TODO: 2020/2/19 移除旧用户的角色信息 并设置新的角色信息
        }
        smShop.setId(id);
        BeanUtils.copyProperties(param, smShop);
        updateById(smShop);
        return smShop;
    }


    private void valid(Long id, Long userId) {

        int count = count(new LambdaQueryWrapper<SmShop>().eq(SmShop::getUserId, userId)
                .ne(id != null, SmShop::getId, id));
        if (count > 0) {
            throw new ExistsException("该用户已经拥有一个店铺了");
        }

    }
}
