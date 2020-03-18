package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.entity.OmCommentScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-02-29
 */
public interface OmCommentScoreMapper extends BaseMapper<OmCommentScore> {

    Float getRate(@Param("shopId")Long shopId);

    Float getScore(@Param("id") Long id);
}
