package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.entity.PmBanner;
import com.zex.cloud.haircut.mapper.PmBannerMapper;
import com.zex.cloud.haircut.params.PmBannerParam;
import com.zex.cloud.haircut.service.IPmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-05
 */
@Service
public class PmBannerServiceImpl extends ServiceImpl<PmBannerMapper, PmBanner> implements IPmBannerService {

    @Override
    public PmBanner save(PmBannerParam param) {
        PmBanner pmBanner  = new PmBanner();
        BeanUtils.copyProperties(param,pmBanner);
        pmBanner.setEnableStatus(true);
        save(pmBanner);
        return pmBanner;
    }

    @Override
    public PmBanner update(Long id, PmBannerParam param) {
        PmBanner pmBanner  = new PmBanner();
        BeanUtils.copyProperties(param,pmBanner);
        pmBanner.setId(id);
        updateById(pmBanner);
        return pmBanner;
    }

    @Override
    public void enableStatus(Long id, Boolean enableStatus) {
        PmBanner pmBanner  = new PmBanner();
        pmBanner.setId(id);
        pmBanner.setEnableStatus(enableStatus);
        updateById(pmBanner);
    }
}
