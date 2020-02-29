package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.OmFlower;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.entity.OmOrder;

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

}
