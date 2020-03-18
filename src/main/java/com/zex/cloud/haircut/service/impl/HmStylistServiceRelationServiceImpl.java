package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.HmStylistServiceRelation;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.mapper.HmStylistServiceRelationMapper;
import com.zex.cloud.haircut.params.HmStylistServiceParam;
import com.zex.cloud.haircut.service.IHmStylistServiceRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
public class HmStylistServiceRelationServiceImpl extends ServiceImpl<HmStylistServiceRelationMapper, HmStylistServiceRelation> implements IHmStylistServiceRelationService {

    @Override
    public void removeByStylistId(Long id) {
        remove(new LambdaQueryWrapper<HmStylistServiceRelation>().eq(HmStylistServiceRelation::getStylistId,id));
    }

    @Override
    public void updateRelations(Long id, List<HmStylistServiceParam> services) {
        removeByStylistId(id);
        if (CollectionUtils.isNotEmpty(services)){
            List<HmStylistServiceRelation> list = services.stream().flatMap((Function<HmStylistServiceParam, Stream<HmStylistServiceRelation>>) hmStylistServiceParam -> {
                HmStylistServiceRelation relation = new HmStylistServiceRelation();
                relation.setFemalePrice(hmStylistServiceParam.getFemalePrice());
                relation.setMalePrice(hmStylistServiceParam.getMalePrice());
                relation.setServiceId(hmStylistServiceParam.getServiceId());
                relation.setStylistId(id);
                return Stream.of(relation);
            }).collect(Collectors.toList());
            saveBatch(list);
        }

    }

    @Override
    public BigDecimal getPriceByServiceIdStylistIdAndSex(Long serviceId, Long stylistId, GenderType genderType) {
        HmStylistServiceRelation relation = getOne(new LambdaQueryWrapper<HmStylistServiceRelation>()
                .eq(HmStylistServiceRelation::getServiceId,serviceId)
                .eq(HmStylistServiceRelation::getStylistId,stylistId));
        if (relation == null){
            throw new NotFoundException("该服务不存在: serviceId = " + serviceId + "stylistId = " + stylistId );
        }
        if (genderType == GenderType.MALE){
            return relation.getMalePrice();
        }else {
            return relation.getFemalePrice();
        }
    }

    @Override
    public List<HmStylistServiceRelation> getByStylistId(Long stylistId) {
        return list(new LambdaQueryWrapper<HmStylistServiceRelation>()
                .eq(HmStylistServiceRelation::getStylistId,stylistId));
    }

    @Override
    public BigDecimal getMinAmount(Long washCutBlowId, Long shopId, Long stylistId) {
        return baseMapper.getMinAmount(washCutBlowId,shopId,stylistId);
    }


}
