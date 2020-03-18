package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.OmCommentScore;
import com.zex.cloud.haircut.mapper.OmCommentScoreMapper;
import com.zex.cloud.haircut.service.IOmCommentScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-29
 */
@Service
public class OmCommentScoreServiceImpl extends ServiceImpl<OmCommentScoreMapper, OmCommentScore> implements IOmCommentScoreService {

    @Override
    public Float getRate(Long id) {
        return baseMapper.getRate(id);
    }

    @Override
    public Float score(Long id) {
        return baseMapper.getScore(id);
    }

    @Override
    public int stylistGoodCommentCount(Long id) {
        return count(new LambdaQueryWrapper<OmCommentScore>().eq(OmCommentScore::getStylistId,id)
        .ge(OmCommentScore::getScore,4));
    }
}
