package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.HmDomain;
import com.zex.cloud.haircut.entity.HmDomain;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.mapper.HmDomainMapper;
import com.zex.cloud.haircut.params.BaseTitleParam;
import com.zex.cloud.haircut.service.IHmDomainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.service.IHmStylistDomainRelationService;
import com.zex.cloud.haircut.service.ISmShopServiceRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
@Service
public class HmDomainServiceImpl extends ServiceImpl<HmDomainMapper, HmDomain> implements IHmDomainService {

    @Autowired
    private IHmStylistDomainRelationService iHmStylistDomainRelationService;
    @Override
    public HmDomain update(Long id, BaseTitleParam param) {
        valid(id,param.getName());
        HmDomain title = new HmDomain();
        title.setDescription(param.getDescription());
        title.setIcon(param.getIcon());
        title.setName(param.getName());
        title.setId(id);
        updateById(title);
        return title;
    }

    private void valid(Long id, String name) {
        int count = count(new LambdaQueryWrapper<HmDomain>()
                .eq(HmDomain::getName,name)
                .ne(id != null,HmDomain::getId,id));
        if (count> 0){
            throw new ExistsException("名称已存在");
        }
    }

    @Override
    public HmDomain save(BaseTitleParam param) {
        valid(null,param.getName());
        HmDomain title = new HmDomain();
        title.setDescription(param.getDescription());
        title.setIcon(param.getIcon());
        title.setName(param.getName());
        title.setSeq(param.getSeq());
        save(title);
        return title;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        removeById(id);
        iHmStylistDomainRelationService.removeByDomainId(id);
    }
}
