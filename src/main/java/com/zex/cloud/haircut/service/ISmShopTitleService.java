package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmShopTitle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.ShopTitleType;
import com.zex.cloud.haircut.params.ShopTitleParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface ISmShopTitleService extends IService<SmShopTitle> {

    SmShopTitle update(Long id, ShopTitleParam param);

    SmShopTitle save(ShopTitleParam param);

    void delete(Long id);

}
