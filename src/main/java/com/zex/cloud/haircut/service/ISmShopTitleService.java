package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmShopTitle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.BaseTitleParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface ISmShopTitleService extends IService<SmShopTitle> {

    SmShopTitle update(Long id, BaseTitleParam param);

    SmShopTitle save(BaseTitleParam param);

    void delete(Long id);

}
