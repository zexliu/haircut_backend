package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.HmDomain;
import com.zex.cloud.haircut.entity.HmStylistDomainRelation;
import com.zex.cloud.haircut.mapper.HmStylistDomainRelationMapper;
import com.zex.cloud.haircut.service.IHmStylistDomainRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
@Service
public class HmStylistDomainRelationServiceImpl extends ServiceImpl<HmStylistDomainRelationMapper, HmStylistDomainRelation> implements IHmStylistDomainRelationService {

    @Override
    public void removeByStylistId(Long id) {
        remove(new LambdaQueryWrapper<HmStylistDomainRelation>().eq(HmStylistDomainRelation::getStylistId, id));
    }

    @Override
    public void removeByDomainId(Long id) {
        remove(new LambdaQueryWrapper<HmStylistDomainRelation>().eq(HmStylistDomainRelation::getDomainId, id));

    }

    @Override
    public void updateRelations(Long id, List<Long> domainIds) {
        removeByStylistId(id);
        if (CollectionUtils.isNotEmpty(domainIds)) {
            List<HmStylistDomainRelation> list = domainIds.stream().flatMap((Function<Long, Stream<HmStylistDomainRelation>>) aLong -> {
                HmStylistDomainRelation relation = new HmStylistDomainRelation();
                relation.setStylistId(id);
                relation.setDomainId(aLong);
                return Stream.of(relation);
            }).collect(Collectors.toList());
            saveBatch(list);
        }
    }

    @Override
    public List<HmDomain> getByStylistId(Long id) {
        return baseMapper.getByStylistId(id);
    }
}
