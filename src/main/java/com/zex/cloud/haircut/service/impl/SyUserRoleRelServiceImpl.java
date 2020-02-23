package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SyUserRoleRel;
import com.zex.cloud.haircut.mapper.SyUserRoleRelMapper;
import com.zex.cloud.haircut.service.ISyUserRoleRelService;
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
public class SyUserRoleRelServiceImpl extends ServiceImpl<SyUserRoleRelMapper, SyUserRoleRel> implements ISyUserRoleRelService {
    @Override
    public void updateRelations(Long id, List<Long> roleIds) {
        remove(new LambdaQueryWrapper<SyUserRoleRel>().eq(SyUserRoleRel::getUserId,id));
        if (CollectionUtils.isNotEmpty(roleIds)){
            List<SyUserRoleRel> list = roleIds.stream().flatMap((Function<Long, Stream<SyUserRoleRel>>) aLong -> {
                SyUserRoleRel rel = new SyUserRoleRel();
                rel.setRoleId(aLong);
                rel.setUserId(id);
                return Stream.of(rel);
            }).collect(Collectors.toList());
            saveBatch(list);
        }
    }

    @Override
    public List<String> getRequestRoles(Long userId) {
        return baseMapper.queryRolesByUserId(userId);
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long id) {
        return baseMapper.getRoleIdsByUserId(id);
    }

    @Override
    public void save(Long roleId, Long userId) {
        SyUserRoleRel rel = new SyUserRoleRel();
        rel.setRoleId(roleId);
        rel.setUserId(userId);
        save(rel);
    }

    @Override
    public void remove(Long roleId, Long userId) {
        remove(new LambdaQueryWrapper<SyUserRoleRel>()
                .eq(SyUserRoleRel::getRoleId,roleId)
                .eq(SyUserRoleRel::getUserId,userId));
    }
}
