package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.HmStylist;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.HmStylistParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface IHmStylistService extends IService<HmStylist> {

    HmStylist save(HmStylistParam param, Long shopId);

    HmStylist update(Long id, HmStylistParam param, Long shopId);

    void delete(Long id, Long shopId);

}
