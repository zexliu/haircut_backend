package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.HmService;
import com.zex.cloud.haircut.entity.HmService;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.mapper.HmServiceMapper;
import com.zex.cloud.haircut.params.HmServiceParam;
import com.zex.cloud.haircut.params.HmServiceParam;
import com.zex.cloud.haircut.service.IHmServiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class HmServiceServiceImpl extends ServiceImpl<HmServiceMapper, HmService> implements IHmServiceService {


    @Override
    public HmService update(Long id, HmServiceParam param) {
        valid(id,param.getName());
        HmService title = new HmService();
        title.setDescription(param.getDescription());
        title.setIcon(param.getIcon());
        title.setName(param.getName());
        title.setId(id);
        updateById(title);
        return title;
    }

    private void valid(Long id, String name) {
        int count = count(new LambdaQueryWrapper<HmService>()
                .eq(HmService::getName,name)
                .ne(id != null,HmService::getId,id));
        if (count> 0){
            throw new ExistsException("名称已存在");
        }
    }

    @Override
    public HmService save(HmServiceParam param) {
        valid(null,param.getName());
        HmService title = new HmService();
        title.setDescription(param.getDescription());
        title.setIcon(param.getIcon());
        title.setName(param.getName());
        title.setSeq(param.getSeq());
        title.setGroupStatus(param.getGroupStatus());
        save(title);
        return title;
    }

  
}
