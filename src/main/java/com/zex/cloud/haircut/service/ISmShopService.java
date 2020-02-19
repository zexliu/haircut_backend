package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.SmShop;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import com.zex.cloud.haircut.params.SmShopParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
public interface ISmShopService extends IService<SmShop> {

   IPage<SmShop> list(Page<SmShop> convert, String keywords, ShopWorkStatus workStatus, String provinceCode, String cityCode, String districtCode, Double longitude, Double latitude);

    SmShop customSave(SmShop smShop);


    SmShop update(Long id, SmShopParam param, Long aLong);

    SmShop adminUpdate(Long id, SmShopParam param);

}
