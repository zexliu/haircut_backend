package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SmShopTitle;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.mapper.SmShopTitleMapper;
import com.zex.cloud.haircut.params.BaseTitleParam;
import com.zex.cloud.haircut.service.ISmShopServiceRelationService;
import com.zex.cloud.haircut.service.ISmShopTitleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SmShopTitleServiceImpl extends ServiceImpl<SmShopTitleMapper, SmShopTitle> implements ISmShopTitleService {

    @Autowired
    private ISmShopServiceRelationService iSmShopServiceRelationService;
    @Override
    public SmShopTitle update(Long id, BaseTitleParam param) {
        valid(id,param.getName());
        SmShopTitle title = new SmShopTitle();
        title.setDescription(param.getDescription());
        title.setIcon(param.getIcon());
        title.setName(param.getName());
        title.setId(id);
        title.setSeq(param.getSeq());
        updateById(title);
        return title;
    }

    private void valid(Long id, String name) {
        int count = count(new LambdaQueryWrapper<SmShopTitle>()
                .eq(SmShopTitle::getName,name)
                .ne(id != null,SmShopTitle::getId,id));
        if (count> 0){
            throw new ExistsException("名称已存在");
        }
    }

    @Override
    public SmShopTitle save(BaseTitleParam param) {
        valid(null,param.getName());
        SmShopTitle title = new SmShopTitle();
        title.setDescription(param.getDescription());
        title.setIcon(param.getIcon());
        title.setName(param.getName());
        save(title);
        return title;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        removeById(id);
        iSmShopServiceRelationService.removeByTitleId(id);
    }
}
