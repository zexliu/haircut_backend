package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zex.cloud.haircut.entity.SmAgentBrand;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.mapper.SmAgentBrandMapper;
import com.zex.cloud.haircut.service.ISmAgentBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
@Service
public class SmAgentBrandServiceImpl extends ServiceImpl<SmAgentBrandMapper, SmAgentBrand> implements ISmAgentBrandService {

    @Override
    public SmAgentBrand create(SmAgentBrand smAgentBrand) {
        valid(smAgentBrand);
        save(smAgentBrand);
        return smAgentBrand;
    }

    @Override
    public SmAgentBrand update(SmAgentBrand smAgentBrand) {
        valid(smAgentBrand);
        update(smAgentBrand,new LambdaUpdateWrapper<SmAgentBrand>()
        .eq(SmAgentBrand::getAgentId,smAgentBrand.getAgentId()));
        return smAgentBrand;
    }

    private void valid(SmAgentBrand smAgentBrand) {
        int count = count(new LambdaQueryWrapper<SmAgentBrand>().eq(SmAgentBrand::getBrandNo,smAgentBrand.getBrandNo())
        .eq(SmAgentBrand::getAgentId,smAgentBrand.getAgentId())
        .ne(smAgentBrand.getId() != null,SmAgentBrand::getId,smAgentBrand.getId()));
        if (count > 0 ){
            throw new ExistsException("卡号已存在");
        }
    }
}
