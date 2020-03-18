package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.OmFlower;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.enums.FlowerType;
import com.zex.cloud.haircut.vo.OmFlowerVo;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
public interface IOmFlowerService extends IService<OmFlower> {

    void onPayHook(OmOrder omOrder);

    IPage<OmFlowerVo> flowerVoPage(Page<OmFlowerVo> page,  FlowerType type, Long userId, Long shopId, LocalDateTime startAt, LocalDateTime endAt);

}
