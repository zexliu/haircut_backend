package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.AmRegion;
import com.zex.cloud.haircut.entity.SyPermissionModule;
import com.zex.cloud.haircut.mapper.AmRegionMapper;
import com.zex.cloud.haircut.response.AbstractTree;
import com.zex.cloud.haircut.response.AmRegionTree;
import com.zex.cloud.haircut.response.RegionDetail;
import com.zex.cloud.haircut.response.SyPermissionModuleTree;
import com.zex.cloud.haircut.service.IAmRegionService;
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
 * @since 2020-02-19
 */
@Service
public class AmRegionServiceImpl extends ServiceImpl<AmRegionMapper, AmRegion> implements IAmRegionService {

    @Override
    public List<AmRegion> list(Integer parentId, Integer level) {
        return list(new LambdaQueryWrapper<AmRegion>()
                .eq(parentId != null,AmRegion::getParentId,parentId)
                .le(AmRegion::getLevel,level));
    }

    @Override
    public List<AmRegionTree> tree(Integer level) {
        List<AmRegionTree> list = list(new LambdaQueryWrapper<AmRegion>()
                .orderByAsc(AmRegion::getAdCode)).stream().flatMap((Function<AmRegion, Stream<AmRegionTree>>) region -> {
            AmRegionTree tree = new AmRegionTree();
            tree.setName(region.getName());
            tree.setId(region.getId());
            tree.setParentId(region.getParentId());
            tree.setLevel(region.getLevel());
            tree.setAdCode(region.getAdCode());
            return Stream.of(tree);
        }).collect(Collectors.toList());
        return AbstractTree.listToTree(list);
    }

    @Override
    public RegionDetail detailByCode(String adCode) {
        return baseMapper.detailByCode(adCode);
    }
}
