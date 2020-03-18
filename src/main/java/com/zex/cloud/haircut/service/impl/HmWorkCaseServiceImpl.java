package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import com.zex.cloud.haircut.entity.HmWorkCase;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.mapper.HmWorkCaseMapper;
import com.zex.cloud.haircut.params.HmWorkCaseParam;
import com.zex.cloud.haircut.service.IHmWorkCaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
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
public class HmWorkCaseServiceImpl extends ServiceImpl<HmWorkCaseMapper, HmWorkCase> implements IHmWorkCaseService {

    @Override
    public void removeByStylistId(Long id) {
        remove(new LambdaQueryWrapper< HmWorkCase>().eq(HmWorkCase::getStylistId,id));
    }

    @Override
    public HmWorkCase save(HmWorkCaseParam param,Long shopId) {
        HmWorkCase hmWorkCase = new HmWorkCase();
        BeanUtils.copyProperties(param,hmWorkCase);
        hmWorkCase.setShopId(shopId);
        save(hmWorkCase);
        return hmWorkCase;
    }

    @Override
    public HmWorkCase update(Long id, HmWorkCaseParam param,Long shopId) {
        HmWorkCase hmWorkCase = getById(id);
        if (hmWorkCase == null || !hmWorkCase.getShopId().equals(shopId)){
            throw new ForbiddenException();
        }
        BeanUtils.copyProperties(param,hmWorkCase);
        hmWorkCase.setId(id);
        updateById(hmWorkCase);
        return hmWorkCase;
    }

    @Override
    public void delete(Long id, Long shopId) {
        HmWorkCase hmWorkCase = getById(id);
        if (hmWorkCase == null || !hmWorkCase.getShopId().equals(shopId)){
            throw new ForbiddenException();
        }
        removeById(id);
    }

    @Override
    public List<HmWorkCase> getByStylistId(Long id) {
        return list(new LambdaQueryWrapper<HmWorkCase>().eq(HmWorkCase::getStylistId,id));
    }

    @Override
    public void updateWorkCases(Long id, Long shopId, List<HmWorkCaseParam> workCases) {
        removeByStylistId(id);
        if (CollectionUtils.isEmpty(workCases)){
            return;
        }
        List<HmWorkCase> cases = workCases.stream().flatMap(new Function<HmWorkCaseParam, Stream<HmWorkCase>>() {
            @Override
            public Stream<HmWorkCase> apply(HmWorkCaseParam hmWorkCaseParam) {
                HmWorkCase workCase = new HmWorkCase();
                BeanUtils.copyProperties(hmWorkCaseParam,workCase);
                workCase.setShopId(shopId);
                workCase.setStylistId(id);
                return Stream.of(workCase);
            }
        }).collect(Collectors.toList());

        saveBatch(cases);
    }
}
