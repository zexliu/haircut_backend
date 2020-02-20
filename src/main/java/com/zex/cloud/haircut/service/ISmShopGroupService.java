package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmShopGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.SmShopGroupParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface ISmShopGroupService extends IService<SmShopGroup> {


    SmShopGroup create(SmShopGroupParam param, Long shopId);

    SmShopGroup update(Long id, SmShopGroupParam param, Long shopId);

    void delete(Long id, Long shopId);

}
