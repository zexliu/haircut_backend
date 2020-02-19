package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SyGroupUserRel;
import com.zex.cloud.haircut.mapper.SyGroupUserRelMapper;
import com.zex.cloud.haircut.service.ISyGroupUserRelService;
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
public class SyGroupUserRelServiceImpl extends ServiceImpl<SyGroupUserRelMapper, SyGroupUserRel> implements ISyGroupUserRelService {
    @Override
    public void updateRelations(Long id, List<Long> groupIds) {
        remove(new LambdaQueryWrapper<SyGroupUserRel>().eq(SyGroupUserRel::getUserId,id));
        if (CollectionUtils.isNotEmpty(groupIds)){
            List<SyGroupUserRel> list = groupIds.stream().flatMap((Function<Long, Stream<SyGroupUserRel>>) aLong -> {
                SyGroupUserRel rel = new SyGroupUserRel();
                rel.setGroupId(aLong);
                rel.setUserId(id);
                return Stream.of(rel);
            }).collect(Collectors.toList());
            saveBatch(list);
        }
    }

    @Override
    public void removeByGroupId(Long id) {
        remove(new LambdaQueryWrapper<SyGroupUserRel>().eq(SyGroupUserRel::getGroupId,id));
    }

    @Override
    public List<Long> getGroupIdsByUserId(Long id) {
        return baseMapper.getGroupIdsByUserId(id);
    }
}
