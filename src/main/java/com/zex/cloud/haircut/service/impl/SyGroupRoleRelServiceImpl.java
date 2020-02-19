package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SyGroupRoleRel;
import com.zex.cloud.haircut.entity.SyUserGroup;
import com.zex.cloud.haircut.mapper.SyGroupRoleRelMapper;
import com.zex.cloud.haircut.service.ISyGroupRoleRelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
@Service
public class SyGroupRoleRelServiceImpl extends ServiceImpl<SyGroupRoleRelMapper, SyGroupRoleRel> implements ISyGroupRoleRelService {

    @Override
    public void removeByGroupId(Long id) {
        remove(new LambdaQueryWrapper<SyGroupRoleRel>().eq(SyGroupRoleRel::getGroupId,id));
    }

    @Override
    public List<Long> getRoleIdsByGroupId(Long id) {
        return baseMapper.getRoleIdsByGroupId(id);
    }

    @Override
    public void updateRelations(Long id, List<Long> roleIds) {
        removeByGroupId(id);

        if (CollectionUtils.isNotEmpty(roleIds)){
            List<SyGroupRoleRel> list = roleIds.stream().flatMap((Function<Long, Stream<SyGroupRoleRel>>) aLong -> {
                SyGroupRoleRel rel = new SyGroupRoleRel();
                rel.setGroupId(id);
                rel.setRoleId(aLong);
                return Stream.of(rel);
            }).collect(Collectors.toList());
            saveBatch(list);
        }

    }
}
