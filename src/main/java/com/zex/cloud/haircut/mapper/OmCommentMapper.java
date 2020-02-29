package com.zex.cloud.haircut.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.OmComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.response.OmOrderCommentInfo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-02-29
 */
public interface OmCommentMapper extends BaseMapper<OmComment> {

    IPage<OmOrderCommentInfo> orderCommentPage(Page<OmOrderCommentInfo> page, @Param("shopId") Long shopId, @Param("orderId") Long orderId, @Param("levelSql") String levelSql);

}
