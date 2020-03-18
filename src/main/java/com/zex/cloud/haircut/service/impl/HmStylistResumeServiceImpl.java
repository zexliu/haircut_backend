package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.HmStylistResume;
import com.zex.cloud.haircut.mapper.HmStylistResumeMapper;
import com.zex.cloud.haircut.params.HmStyListResumeParam;
import com.zex.cloud.haircut.service.IHmStylistResumeService;
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
 * @since 2020-03-09
 */
@Service
public class HmStylistResumeServiceImpl extends ServiceImpl<HmStylistResumeMapper, HmStylistResume> implements IHmStylistResumeService {

    @Override
    public void updateResumes(Long id, List<HmStyListResumeParam> resumes) {
        remove(new LambdaQueryWrapper<HmStylistResume>().eq(HmStylistResume::getStylistId,id));
        if (CollectionUtils.isNotEmpty(resumes)){
            List<HmStylistResume> collect = resumes.stream().flatMap((Function<HmStyListResumeParam, Stream<HmStylistResume>>) hmStyListResumeParam -> {
                HmStylistResume resume = new HmStylistResume();
                BeanUtils.copyProperties(hmStyListResumeParam, resume);
                resume.setStylistId(id);
                return Stream.of(resume);
            }).collect(Collectors.toList());
            saveBatch(collect);
        }
    }

    @Override
    public void removeByStylist(Long id) {
        remove(new LambdaQueryWrapper<HmStylistResume>().eq(HmStylistResume::getStylistId,id));
    }

    @Override
    public List<HmStylistResume> getByStylistId(Long stylistId) {
        return list(new LambdaQueryWrapper<HmStylistResume>()
                .eq(HmStylistResume::getStylistId,stylistId));
    }
}
