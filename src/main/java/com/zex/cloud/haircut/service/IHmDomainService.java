package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.HmDomain;
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
public interface IHmDomainService extends IService<HmDomain> {

    HmDomain save(BaseTitleParam param);

    HmDomain update(Long id, BaseTitleParam param);

    void delete(Long id);
}
