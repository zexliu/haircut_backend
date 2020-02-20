package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.HmWorkCase;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.mapper.HmWorkCaseMapper;
import com.zex.cloud.haircut.params.HmWorkCaseParam;
import com.zex.cloud.haircut.service.IHmWorkCaseService;
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
public class HmWorkCaseServiceImpl extends ServiceImpl<HmWorkCaseMapper, HmWorkCase> implements IHmWorkCaseService {

    @Override
    public void removeByStylistId(Long id) {
        remove(new LambdaQueryWrapper< HmWorkCase>().eq(HmWorkCase::getStylistId,id));
    }

    @Override
    public HmWorkCase save(HmWorkCaseParam param,Long shopId) {
        HmWorkCase hmWorkCase = new HmWorkCase();
        BeanUtils.copyProperties(param,hmWorkCase);
        hmWorkCase.setShopId(shopId);
        save(hmWorkCase);
        return hmWorkCase;
    }

    @Override
    public HmWorkCase update(Long id, HmWorkCaseParam param,Long shopId) {
        HmWorkCase hmWorkCase = getById(id);
        if (hmWorkCase == null || !hmWorkCase.getShopId().equals(shopId)){
            throw new ForbiddenException();
        }
        BeanUtils.copyProperties(param,hmWorkCase);
        hmWorkCase.setId(id);
        updateById(hmWorkCase);
        return hmWorkCase;
    }

    @Override
    public void delete(Long id, Long shopId) {
        HmWorkCase hmWorkCase = getById(id);
        if (hmWorkCase == null || !hmWorkCase.getShopId().equals(shopId)){
            throw new ForbiddenException();
        }
        removeById(id);
    }
}
