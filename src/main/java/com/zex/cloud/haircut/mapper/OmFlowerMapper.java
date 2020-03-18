package com.zex.cloud.haircut.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.OmFlower;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.enums.FlowerType;
import com.zex.cloud.haircut.vo.OmFlowerVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
public interface OmFlowerMapper extends BaseMapper<OmFlower> {

    IPage<OmFlowerVo> flowerVOPage(Page<OmFlowerVo> page,
                                   @Param("userId") Long userId,
                                   @Param("shopId")Long shopId,
                                   @Param("type")FlowerType type,
                                   @Param("startAt")LocalDateTime startAt,
                                   @Param("endAt")LocalDateTime endAt);

}
