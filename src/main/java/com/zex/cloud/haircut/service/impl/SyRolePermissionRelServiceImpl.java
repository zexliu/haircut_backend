package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SyRolePermissionRel;
import com.zex.cloud.haircut.mapper.SyRolePermissionRelMapper;
import com.zex.cloud.haircut.service.ISyRolePermissionRelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.util.RedisKeys;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
public class SyRolePermissionRelServiceImpl extends ServiceImpl<SyRolePermissionRelMapper, SyRolePermissionRel> implements ISyRolePermissionRelService {

    @Autowired
    private RedisTemplate<String,Object>redisTemplate;
    @Override
    public void updateRelations(Long id, List<Long> permissionIds) {
        removeRelationsByRoleId(id);
        if (CollectionUtils.isNotEmpty(permissionIds)){
            List<SyRolePermissionRel> list = permissionIds.stream().flatMap((Function<Long, Stream<SyRolePermissionRel>>) aLong -> {
                SyRolePermissionRel rel = new SyRolePermissionRel();
                rel.setPermissionId(aLong);
                rel.setRoleId(id);
                return Stream.of(rel);
            }).collect(Collectors.toList());
            saveBatch(list);
        }
        redisTemplate.delete(RedisKeys.PERMISSION_ROLE);

    }

    @Override
    public void removeRelationsByRoleId(Long id) {
        remove(new LambdaQueryWrapper<SyRolePermissionRel>()
                .eq(SyRolePermissionRel::getRoleId,id));
        redisTemplate.delete(RedisKeys.PERMISSION_ROLE);
    }

    @Override
    public List<Long> getPermissionIdsByRoleId(Long id) {
        return baseMapper.getPermissionIdsByRoleId(id);
    }
}
