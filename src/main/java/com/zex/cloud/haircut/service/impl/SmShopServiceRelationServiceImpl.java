package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SmShopServiceRelation;
import com.zex.cloud.haircut.mapper.SmShopServiceRelationMapper;
import com.zex.cloud.haircut.service.ISmShopServiceRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2020-02-20
 */
@Service
public class SmShopServiceRelationServiceImpl extends ServiceImpl<SmShopServiceRelationMapper, SmShopServiceRelation> implements ISmShopServiceRelationService {

    @Override
    public void removeByTitleId(Long id) {
        remove(new LambdaQueryWrapper<SmShopServiceRelation>().eq(SmShopServiceRelation::getTitleId,id));
    }

    @Override
    public void updateRelations(Long shopId, List<Long> titleIds) {
        removeByShopId(shopId);
        List<SmShopServiceRelation> relations = titleIds.stream().flatMap((Function<Long, Stream<SmShopServiceRelation>>) aLong -> {
            SmShopServiceRelation relation = new SmShopServiceRelation();
            relation.setShopId(shopId);
            relation.setTitleId(aLong);
            return Stream.of(relation);
        }).collect(Collectors.toList());
        saveBatch(relations);
    }

    @Override
    public List<Long> getServiceIdsByShopId(Long shopId) {
        return baseMapper.getServiceIdsByShopId(shopId);
    }

    private void removeByShopId(Long shopId) {
        remove(new LambdaQueryWrapper<SmShopServiceRelation>().eq(SmShopServiceRelation::getShopId,shopId));

    }
}
