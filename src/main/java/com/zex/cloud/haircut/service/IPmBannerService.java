package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.PmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.PmBannerParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-05
 */
public interface IPmBannerService extends IService<PmBanner> {

    PmBanner save(PmBannerParam param);

    PmBanner update(Long id, PmBannerParam param);

    void enableStatus(Long id, Boolean enableStatus);

}
