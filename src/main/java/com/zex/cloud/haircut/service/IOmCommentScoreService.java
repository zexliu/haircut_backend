package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.OmCommentScore;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-29
 */
public interface IOmCommentScoreService extends IService<OmCommentScore> {

    Float getRate(Long id);

    Float score(Long id);

    int stylistGoodCommentCount(Long id);

}
