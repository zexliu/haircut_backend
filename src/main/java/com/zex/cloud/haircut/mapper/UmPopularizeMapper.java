package com.zex.cloud.haircut.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.UmPopularize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.enums.PopularizeStatus;
import com.zex.cloud.haircut.enums.PopularizeType;
import com.zex.cloud.haircut.response.UmPopularizeUser;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-03-03
 */
public interface UmPopularizeMapper extends BaseMapper<UmPopularize> {

    IPage<UmPopularizeUser> list(Page<UmPopularizeUser> page, @Param("targetId") Long targetId,
                                 @Param("popularizeStatus")PopularizeStatus popularizeStatus,
                                 @Param("popularizeType")PopularizeType popularizeType,
                                 @Param("startAt") LocalDateTime startAt,
                                 @Param("endAt") LocalDateTime endAt);

}
