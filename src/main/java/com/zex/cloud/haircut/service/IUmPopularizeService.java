package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.UmPopularize;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.PopularizeStatus;
import com.zex.cloud.haircut.enums.PopularizeType;
import com.zex.cloud.haircut.response.UmPopularizeUser;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-03
 */
public interface IUmPopularizeService extends IService<UmPopularize> {

    IPage<UmPopularizeUser> page(Page<UmPopularizeUser> convert, Long targetId, PopularizeType popularizeType, PopularizeStatus popularizeStatus, LocalDateTime startAt, LocalDateTime endAt);

    UmPopularize getByUserId(Long userId);

}
